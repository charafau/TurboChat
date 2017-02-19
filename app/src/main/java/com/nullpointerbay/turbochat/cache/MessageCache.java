package com.nullpointerbay.turbochat.cache;

import com.nullpointerbay.turbochat.model.Message;

import java.util.List;

import io.reactivex.Observable;


public interface MessageCache {

    Observable<List<Message>> getMessages();

    void writeToCache(List<Message> messages);

    void addMessage(Message serverMessage);
}
