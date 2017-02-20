package com.nullpointerbay.turbochat.di;

import android.content.Context;

import com.nullpointerbay.turbochat.cache.MessageCache;
import com.nullpointerbay.turbochat.cache.MockMessageCacheImpl;
import com.nullpointerbay.turbochat.repository.EmojiRepository;
import com.nullpointerbay.turbochat.repository.EmojiRepositoryImpl;
import com.nullpointerbay.turbochat.repository.MessageRepository;
import com.nullpointerbay.turbochat.repository.MockMessageRepository;
import com.nullpointerbay.turbochat.repository.MockTeamRepositoryImpl;
import com.nullpointerbay.turbochat.repository.MockUserRepository;
import com.nullpointerbay.turbochat.repository.TeamRepository;
import com.nullpointerbay.turbochat.repository.UserRepository;
import com.nullpointerbay.turbochat.utils.ImageLoader;
import com.nullpointerbay.turbochat.utils.MockImageLoader;
import com.nullpointerbay.turbochat.utils.MockUserResolverImpl;
import com.nullpointerbay.turbochat.utils.UserResolver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

@Module
public class MockDataModule {

    @Provides
    public UserResolver provideMockUserResolver() {
        return new MockUserResolverImpl();
    }

    @Provides
    public TeamRepository provideMockTeamRepository(Retrofit retrofit, MockRetrofit mockRetrofit,
                                                    NetworkBehavior networkBehavior) {
        return new MockTeamRepositoryImpl(retrofit, mockRetrofit, networkBehavior);
    }

    @Provides
    public MessageRepository provideMessageRepository(Retrofit retrofit, MockRetrofit mockRetrofit,
                                                      NetworkBehavior networkBehavior) {
        return new MockMessageRepository(retrofit, mockRetrofit, networkBehavior);
    }

    @Provides
    public ImageLoader provideMockImageLoader() {
        return new MockImageLoader();
    }

    @Provides
    public UserRepository provideMockUserRepository(Retrofit retrofit) {
        return new MockUserRepository(retrofit);
    }

    @Provides
    public EmojiRepository provideEmojiRepository(Context context) {
        return new EmojiRepositoryImpl(context);
    }

    @Provides
    @Singleton
    public MessageCache provideMessageCache() {
        return new MockMessageCacheImpl();
    }

    @Provides
    public NetworkBehavior provideNetworkBehavior() {
        return  NetworkBehavior.create();
    }

    @Provides
    @Singleton
    public MockRetrofit provideNetworkRetrofit(Retrofit retrofit, NetworkBehavior networkBehavior) {
        final MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(networkBehavior).build();
        return mockRetrofit;
    }
}
