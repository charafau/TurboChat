package com.nullpointerbay.turbochat.utils;

import com.nullpointerbay.turbochat.model.User;

/**
 * Created by charafau on 2017/02/12.
 */

public class UserResolverImpl implements UserResolver {
    @Override
    public User getLoggedInUser() {
        return new User(1L, "bruon", "Bruno Kanode", "u_bruno");
    }
}
