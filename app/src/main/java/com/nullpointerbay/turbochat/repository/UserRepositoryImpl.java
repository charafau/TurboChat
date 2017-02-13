package com.nullpointerbay.turbochat.repository;

import com.nullpointerbay.turbochat.model.User;

import io.reactivex.Single;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Single<User> getUser(String nick) {
        return null;
    }
}
