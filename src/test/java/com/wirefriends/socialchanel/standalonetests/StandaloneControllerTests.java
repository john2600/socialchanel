package com.wirefriends.socialchanel.standalonetests;

import com.wirefriends.socialchanel.friends.controllers.FriendController;
import com.wirefriends.socialchanel.friends.model.Friend;
import com.wirefriends.socialchanel.friends.services.FriendService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FriendController.class)
public class StandaloneControllerTests {
    @MockBean
    FriendService friendService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCreateReadDelete() throws Exception {
        Friend friend = new Friend("Gordon", "Moore");
        List<Friend> friendList = Arrays.asList(friend);

        Mockito.when(friendService.findAll()).thenReturn(friendList);

        mockMvc.perform(get("/app/friend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].first-name", is("Gordon")))
        ;
    }
}
