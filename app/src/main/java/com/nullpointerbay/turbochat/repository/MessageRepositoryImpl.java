package com.nullpointerbay.turbochat.repository;

import com.nullpointerbay.turbochat.model.Message;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;


public class MessageRepositoryImpl implements MessageRepository {
    @Override
    public Observable<List<Message>> getMessages() {
        return null;
    }

    @Override
    public Single<Message> sendMessage(Message message) {
        return null;
    }

    @Override
    public Observable<Message> liveMessageStream() {
        return null;
    }
}
