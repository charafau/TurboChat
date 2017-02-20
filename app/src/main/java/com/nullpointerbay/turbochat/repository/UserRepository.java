package com.nullpointerbay.turbochat.repository;

import com.nullpointerbay.turbochat.model.User;

import io.reactivex.Flowable;

/**
 * Created by charafau on 2017/02/13.
 */

public interface UserRepository {

    Flowable<User> getUser(String nick);

}
