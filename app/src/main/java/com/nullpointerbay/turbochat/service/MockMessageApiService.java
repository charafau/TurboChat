package com.nullpointerbay.turbochat.service;

import com.nullpointerbay.turbochat.model.Link;
import com.nullpointerbay.turbochat.model.Message;
import com.nullpointerbay.turbochat.model.User;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import retrofit2.mock.BehaviorDelegate;

public class MockMessageApiService implements MessageApiService {

    private final BehaviorDelegate<MessageApiService> delegate;

    public MockMessageApiService(BehaviorDelegate<MessageApiService> delegate) {
        this.delegate = delegate;
    }


    @Override
    public Single<List<Message>> getMessages() {

        String message = "Good morning! (megusta) (coffee) here is" +
                " some link\n https://www.youtube.com/watch?v=7Ky6ZaodBkU&t=2473s \nshould " +
                "be highlighted and @alex is nice";

        final List<Message> messages = Arrays.asList(
                new Message(1L, message, Arrays.asList("alex"), Arrays.asList("megusta", "coffee"),
                        Arrays.asList(new Link("https://www.youtube.com/watch?v=7Ky6ZaodBkU&t=2473s", "YouTube")),
                        new User(1L, "alex", "Alex Smith", "u_alex"))
        );
        return delegate.returningResponse(messages).getMessages();
    }
}
