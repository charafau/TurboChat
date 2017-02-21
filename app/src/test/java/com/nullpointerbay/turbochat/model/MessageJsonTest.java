package com.nullpointerbay.turbochat.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nullpointerbay.turbochat.testutils.TestUtils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static android.R.attr.id;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
 * Created by charafau on 2017/02/22.
 */
public class MessageJsonTest {


    @Test
    public void shouldReturnJson() throws Exception {
        String message = "hello @alex http://youtube.com/q=look (love) there @yui you go http://twitter.com";

        final List<Link> expectedLinks = asList(
                new Link("http://youtube.com/q=look", "youtube"),
                new Link("http://twitter.com", "twitter"));
        final Message messageModel = new Message(id, message, Arrays.asList("alex", "yui"),
                Arrays.asList("love"), expectedLinks, TestUtils.createMockUser());


        final Gson gson = new GsonBuilder().create();

        String expectedJsonString = "{\"id\":16842960,\"text\":\"hello @alex http://youtube.com/q\\u003dlook (love) " +
                "there @yui you go http://twitter.com\",\"mentions\":[\"alex\",\"yui\"],\"emoticons\":[\"love\"],\"links\":" +
                "[{\"url\":\"http://youtube.com/q\\u003dlook\",\"title\":\"youtube\"},{\"url\":\"http://twitter.com\"" +
                ",\"title\":\"twitter\"}],\"user\":{\"id\":1,\"nick\":\"alex\",\"name\":\"Alex Smith\",\"avatarUrl\":\"u_alex\"}}";

        assertEquals(expectedJsonString, gson.toJson(messageModel).toString());

    }
}