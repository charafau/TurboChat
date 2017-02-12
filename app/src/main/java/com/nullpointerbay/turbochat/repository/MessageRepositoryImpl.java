package com.nullpointerbay.turbochat.repository;

import com.nullpointerbay.turbochat.model.Message;

import java.util.List;

import io.reactivex.Single;


public class MessageRepositoryImpl implements MessageRepository {
    @Override
    public Single<List<Message>> getMessages() {
        return null;
    }
}
