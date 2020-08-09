package com.wirefriends.socialchanel.friends.controllers;

import com.wirefriends.socialchanel.friends.exceptions.FieldErrorMessage;
import com.wirefriends.socialchanel.friends.model.Friend;
import com.wirefriends.socialchanel.friends.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app")
public class FriendController {
    @Autowired
    private FriendService friendService;

    public FriendController(){}

    @PostMapping
    public Friend create(@RequestBody Friend friend) throws ValidationException {
        return friendService.save(friend);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map((fieldError -> new FieldErrorMessage(fieldError.getField(),
                fieldError.getDefaultMessage()))).collect(Collectors.toList());

        return fieldErrorMessages;
    }

    @GetMapping("/friend")
    public Iterable<Friend> read(){
        System.out.println("returning info");
        return friendService.findAll();
    }

    @PutMapping("/friend/{id}")
    public ResponseEntity<Friend> updateStudent(@RequestBody Friend friend, @PathVariable("id")  Long id) throws Exception {
       if(!friendService.findById(id).isPresent()) {
           return new ResponseEntity<>(friend, HttpStatus.BAD_REQUEST);
       }
       friend.setId(id);
       return new ResponseEntity<Friend>(friendService.save(friend),HttpStatus.CREATED);
    }

    @GetMapping("friend/{id}")
    public Friend getFriendByID(@PathVariable("id") Long id){
        Optional<Friend> friend=this.friendService.findById(id);
        if(!friend.isPresent()){
            throw new RuntimeException();
        }
        return friend.get();
    }

    @DeleteMapping("/friend/{id}")
    public void delete(@PathVariable("id") Long id){
        friendService.deleteById((id));
    }

    @GetMapping("/friend/search")
    Iterable<Friend> findByQuery(@RequestParam( value="first", required=false) String firstName, @RequestParam(
            value="last", required = false) String lastName) {
        if (firstName!=null && lastName!=null)
            return this.friendService.findByFirstNameAndLastName(firstName,lastName);
        else if (firstName!=null)
            return this.friendService.findByFirstName(firstName);
        else if(lastName!=null)
            return this.friendService.findByLastName(lastName);
        else
            return friendService.findAll();

    }

    @GetMapping("/wrong")
    public Friend somethingIsWrong() throws ValidationException {
        throw new ValidationException("Somwthing is wrong");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    String exceptionHandler(ValidationException e){
        return e.getMessage();
    }
}

