package com.nullpointerbay.turbochat.repository;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.nullpointerbay.turbochat.model.Message;
import com.nullpointerbay.turbochat.service.MessageApiService;
import com.nullpointerbay.turbochat.service.MockMessageApiService;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class MockMessageRepository implements MessageRepository {

    private final NetworkBehavior networkBehavior = NetworkBehavior.create();
    private final MockMessageApiService mockMessageApiService;


    public MockMessageRepository() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://example.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        final MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(networkBehavior).build();

        final BehaviorDelegate<MessageApiService> delegate = mockRetrofit.create(MessageApiService.class);

        mockMessageApiService = new MockMessageApiService(delegate);
    }

    @Override
    public Single<List<Message>> getMessages() {
        return mockMessageApiService.getMessages();
    }
}
