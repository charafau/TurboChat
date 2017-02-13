package com.nullpointerbay.turbochat.viewmodel;

import com.nullpointerbay.turbochat.model.User;
import com.nullpointerbay.turbochat.repository.UserRepository;

import io.reactivex.Single;

/**
 * Created by charafau on 2017/02/13.
 */

public class ProfileViewModel {

    private final UserRepository userRepository;

    public ProfileViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Single<User> getUserByNick(String nick) {
        return userRepository.getUser(nick);
    }
}
