package com.nullpointerbay.turbochat.testutils;

import com.nullpointerbay.turbochat.model.Link;
import com.nullpointerbay.turbochat.model.Message;
import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.model.User;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static User createMockUser() {
        return new User(1L, "alex", "Alex Smith", "u_alex");
    }

    public static Team createTeam() {
        return new Team("random uuid", "team1", "http://mock.url");
    }

    public static Message createMessage() {
        List<String> mentions = new ArrayList<>();
        mentions.add("@alex");
        mentions.add("@bruno");

        List<String> emojis = new ArrayList<>();
        emojis.add("love");

        List<Link> links = new ArrayList<>();
        links.add(new Link("http://www.google.com/q=hi", "google"));

        return new Message(1L, "sample (love) message @alex, http://www.google.com/q=hi @burno hello ",
                mentions, emojis, links, createMockUser());

    }
}
