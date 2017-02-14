package com.nullpointerbay.turbochat.service;

import com.nullpointerbay.turbochat.model.User;

import io.reactivex.Single;
import retrofit2.http.Query;
import retrofit2.mock.BehaviorDelegate;

public class MockUserApiService implements UserApiService {

    public static final String NICK_BRUNO = "bruno";
    public static final String NICK_ALEX = "alex";
    public static final String NICK_YUI = "yui";
    private final BehaviorDelegate<UserApiService> delegate;

    public MockUserApiService(BehaviorDelegate<UserApiService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Single<User> getUser(@Query("nick") String nick) {

        final User userBruno = new User(2L, NICK_BRUNO, "Bruno Kanode", "u_bruno");
        final User userAlex = new User(1L, NICK_ALEX, "Alex Smith", "u_alex");
        final User userYui = new User(3L, NICK_YUI, "Yui Kanazawa", "u_yui");

        if (nick.startsWith("@")) {
            nick = nick.substring(1);
        }

        User returningUser = userAlex;

        if (NICK_BRUNO.equals(nick)) {
            returningUser = userBruno;
        }
        if (NICK_ALEX.equals(nick)) {
            returningUser = userAlex;
        }
        if (NICK_YUI.equals(nick)) {
            returningUser = userYui;
        }

        return delegate.returningResponse(returningUser).getUser(nick);
    }
}
