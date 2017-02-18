package com.nullpointerbay.turbochat.di;

import android.content.Context;

import com.nullpointerbay.turbochat.cache.MessageCache;
import com.nullpointerbay.turbochat.cache.MessageCacheImpl;
import com.nullpointerbay.turbochat.repository.EmojiRepository;
import com.nullpointerbay.turbochat.repository.EmojiRepositoryImpl;
import com.nullpointerbay.turbochat.repository.MessageRepository;
import com.nullpointerbay.turbochat.repository.MockMessageRepository;
import com.nullpointerbay.turbochat.repository.MockUserRepository;
import com.nullpointerbay.turbochat.repository.TeamRepository;
import com.nullpointerbay.turbochat.repository.TeamRepositoryImpl;
import com.nullpointerbay.turbochat.repository.UserRepository;
import com.nullpointerbay.turbochat.utils.ImageLoader;
import com.nullpointerbay.turbochat.utils.MockImageLoader;
import com.nullpointerbay.turbochat.utils.MockUserResolverImpl;
import com.nullpointerbay.turbochat.utils.UserResolver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockDataModule {

    @Provides
    public UserResolver provideMockUserResolver() {
        return new MockUserResolverImpl();
    }

    @Provides
    public TeamRepository provideMockTeamRepository() {
        return new TeamRepositoryImpl();
    }

    @Provides
    public MessageRepository provideMessageRepository() {
        return new MockMessageRepository();
    }

    @Provides
    public ImageLoader provideMockImageLoader() {
        return new MockImageLoader();
    }

    @Provides
    public UserRepository provideMockUserRepository() {
        return new MockUserRepository();
    }

    @Provides
    public EmojiRepository provideEmojiRepository(Context context) {
        return new EmojiRepositoryImpl(context);
    }

    @Provides
    @Singleton
    public MessageCache provideMessageCache() {
        return new MessageCacheImpl();
    }
}
