package com.nullpointerbay.turbochat.viewmodel;

import com.nullpointerbay.turbochat.model.Message;
import com.nullpointerbay.turbochat.model.User;
import com.nullpointerbay.turbochat.repository.MessageRepository;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;

import static android.R.attr.id;


public class MessageViewModel {

    private final MessageRepository messageRepository;

    PublishSubject<Message> messagePublishSubject = PublishSubject.create();

    public MessageViewModel(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Single<List<Message>> getMessages() {
        return messageRepository.getMessages();
    }

    public void sendMessage(String message) {
        final User userYui = new User(3L, "yui", "Yui Kanazawa", "u_yui");

        final Message m = new Message(id, "some message", Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), userYui);

        messagePublishSubject.onNext(m);

    }

    public Observable<Message> messageStream() {
        return messagePublishSubject;
    }
}
