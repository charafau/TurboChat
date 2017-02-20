package com.nullpointerbay.turbochat.di;

import android.content.Context;

import com.nullpointerbay.turbochat.cache.MessageCache;
import com.nullpointerbay.turbochat.cache.MessageCacheImpl;
import com.nullpointerbay.turbochat.repository.EmojiRepository;
import com.nullpointerbay.turbochat.repository.EmojiRepositoryImpl;
import com.nullpointerbay.turbochat.repository.MessageRepository;
import com.nullpointerbay.turbochat.repository.MessageRepositoryImpl;
import com.nullpointerbay.turbochat.repository.TeamRepository;
import com.nullpointerbay.turbochat.repository.TeamRepositoryImpl;
import com.nullpointerbay.turbochat.repository.UserRepository;
import com.nullpointerbay.turbochat.repository.UserRepositoryImpl;
import com.nullpointerbay.turbochat.utils.ImageLoader;
import com.nullpointerbay.turbochat.utils.PicassoImageLoader;
import com.nullpointerbay.turbochat.utils.UserResolver;
import com.nullpointerbay.turbochat.utils.UserResolverImpl;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DataModule {

    @Provides
    public UserResolver provideUserResolver() {
        return new UserResolverImpl();
    }

    @Provides
    public TeamRepository provideTeamRepository(Retrofit retrofit) {
        return new TeamRepositoryImpl();
    }

    @Provides
    public MessageRepository provideMessageRepository() {
        return new MessageRepositoryImpl();
    }

    @Provides
    public ImageLoader provideImageLoader() {
        return new PicassoImageLoader();
    }

    @Provides
    public UserRepository provideUserRepository(){
        return new UserRepositoryImpl();
    }

    @Provides
    public EmojiRepository provideEmojiRepository(Context context) {
        return new EmojiRepositoryImpl(context);
    }

    @Provides
    public MessageCache provideMessageCache() {
        return new MessageCacheImpl();
    }

}
