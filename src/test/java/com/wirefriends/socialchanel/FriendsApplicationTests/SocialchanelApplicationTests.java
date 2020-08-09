package com.wirefriends.socialchanel.FriendsApplicationTests;

import com.wirefriends.socialchanel.friends.controllers.FriendController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

@SpringBootTest
class SocialchanelApplicationTests {
    @Autowired
    private FriendController friendController;

    @Test
    public void contextLoads(){
        assertThat(friendController, notNullValue());
    }


}
