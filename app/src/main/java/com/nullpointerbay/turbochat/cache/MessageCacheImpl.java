package com.nullpointerbay.turbochat.cache;

import com.nullpointerbay.turbochat.model.Message;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by charafau on 2017/02/21.
 */

public class MessageCacheImpl implements MessageCache {
    @Override
    public Observable<List<Message>> getMessages() {
        return null;
    }

    @Override
    public void writeToCache(List<Message> messages) {

    }

    @Override
    public void addMessage(Message serverMessage) {

    }
}
