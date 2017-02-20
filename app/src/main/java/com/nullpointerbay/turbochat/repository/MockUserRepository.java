package com.nullpointerbay.turbochat.repository;

import com.nullpointerbay.turbochat.model.User;
import com.nullpointerbay.turbochat.service.MockUserApiService;
import com.nullpointerbay.turbochat.service.UserApiService;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Created by charafau on 2017/02/13.
 */

public class MockUserRepository implements UserRepository {

    private final NetworkBehavior networkBehavior = NetworkBehavior.create();
    private final MockUserApiService mockMessageApiService;

    public MockUserRepository(Retrofit retrofit) {

        final MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(networkBehavior).build();

        final BehaviorDelegate<UserApiService> delegate = mockRetrofit.create(UserApiService.class);

        mockMessageApiService = new MockUserApiService(delegate);
    }

    @Override
    public Single<User> getUser(String nick) {
        return mockMessageApiService.getUser(nick);
    }
}
