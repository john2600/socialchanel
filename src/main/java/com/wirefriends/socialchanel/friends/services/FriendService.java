package com.wirefriends.socialchanel.friends.services;

import com.wirefriends.socialchanel.friends.model.Friend;
import org.springframework.data.repository.CrudRepository;

public interface FriendService extends CrudRepository<Friend,Long> {
    Iterable<Friend> findByFirstNameAndLastName(String firstName, String LastName);
    Iterable<Friend> findByFirstName(String firstName);
    Iterable<Friend> findByLastName(String lastName);
}
