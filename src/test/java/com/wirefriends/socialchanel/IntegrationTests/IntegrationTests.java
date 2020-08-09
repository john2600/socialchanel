package com.wirefriends.socialchanel.IntegrationTests;


import com.wirefriends.socialchanel.friends.controllers.FriendController;
import com.wirefriends.socialchanel.friends.model.Friend;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;


@SpringBootTest
public class IntegrationTests {
    @Autowired
    FriendController friendController;

    @Test
    public void testCreateReadDelete() throws ValidationException {
        Friend friend = new Friend("Gordon","Moore");
        Friend friendResult = friendController.create(friend);

        Iterable<Friend> friends = friendController.read();

        assertThat(friends).first().hasFieldOrPropertyWithValue("firstName","Gordon");

        friendController.delete(friendResult.getId());
        assertThat(friendController.read()).isEmpty();

    }

    public void errorHandlingValidationExceptionThrown(){
        //friendController.somethingIsWrong();
    }

    @Test
    public void testErrorHandlingReturnsBadRequest(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/app/wrong";
        try {
            restTemplate.getForEntity(url, String.class);
        }catch (HttpClientErrorException e){
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }
}
