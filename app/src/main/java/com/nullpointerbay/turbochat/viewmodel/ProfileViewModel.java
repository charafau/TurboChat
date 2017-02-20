package com.nullpointerbay.turbochat.viewmodel;

import com.nullpointerbay.turbochat.model.User;
import com.nullpointerbay.turbochat.repository.UserRepository;

import io.reactivex.Flowable;


public class ProfileViewModel {

    private final UserRepository userRepository;

    public ProfileViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flowable<User> getUserByNick(String nick) {
        return userRepository.getUser(nick);
    }
}
