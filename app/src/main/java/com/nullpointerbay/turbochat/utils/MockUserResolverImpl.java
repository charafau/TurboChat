package com.nullpointerbay.turbochat.utils;

import com.nullpointerbay.turbochat.model.User;

/**
 * Created by charafau on 2017/02/12.
 */

public class MockUserResolverImpl implements UserResolver {
    @Override
    public User getLoggedInUser() {
        return new User(1L, "alex", "Alex Smith", "u_alex");
    }
}
