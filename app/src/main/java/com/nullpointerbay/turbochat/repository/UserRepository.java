package com.nullpointerbay.turbochat.repository;

import com.nullpointerbay.turbochat.model.User;

import io.reactivex.Single;

/**
 * Created by charafau on 2017/02/13.
 */

public interface UserRepository {

    Single<User> getUser(String nick);

}
