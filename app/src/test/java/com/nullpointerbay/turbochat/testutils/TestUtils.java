package com.nullpointerbay.turbochat.testutils;

import com.nullpointerbay.turbochat.model.User;

public class TestUtils {

    public static User createMockUser() {
        return new User(1L, "alex", "Alex Smith", "u_alex");
    }

}
