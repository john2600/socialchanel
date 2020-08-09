package com.wirefriends.socialchanel.systemTests;

import com.wirefriends.socialchanel.friends.model.Friend;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class testCreateReadDelete {

    @Test
    public void testCreateReadDelete(){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/app/";

        Friend friend = new Friend("Gordon","Moore");

        ResponseEntity<Friend> entity = restTemplate.postForEntity(url, friend, Friend.class);

        Friend[] friends = restTemplate.getForObject(url+"friend", Friend[].class);

        Assertions.assertThat(friends).extracting(Friend::getFirstName).containsOnly("Gordon");

        restTemplate.delete(url +"friend/"+entity.getBody().getId());

        Assertions.assertThat(restTemplate.getForObject(url+"friend", Friend[].class)).isEmpty();
    }
}
