package com.nullpointerbay.turbochat.repository;

import com.nullpointerbay.turbochat.model.User;

import io.reactivex.Flowable;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Flowable<User> getUser(String nick) {
        return null;
    }
}
