package com.nullpointerbay.turbochat.testutils;

import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.model.User;

public class TestUtils {

    public static User createMockUser() {
        return new User(1L, "alex", "Alex Smith", "u_alex");
    }

    public static Team createTeam() {
        return new Team("random uuid", "team1", "http://mock.url");
    }
}
