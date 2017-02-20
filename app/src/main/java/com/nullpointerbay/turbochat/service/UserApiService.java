package com.nullpointerbay.turbochat.service;

import com.nullpointerbay.turbochat.model.User;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApiService {

    @GET("/user")
    Flowable<User> getUser(@Query("nick") String nick);

}
