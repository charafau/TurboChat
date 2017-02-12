package com.nullpointerbay.turbochat.viewmodel;

import com.nullpointerbay.turbochat.model.Message;
import com.nullpointerbay.turbochat.repository.MessageRepository;

import java.util.List;

import io.reactivex.Single;


public class MessageViewModel {

    private final MessageRepository messageRepository;

    public MessageViewModel(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Single<List<Message>> getMessages() {
        return messageRepository.getMessages();
    }

}
