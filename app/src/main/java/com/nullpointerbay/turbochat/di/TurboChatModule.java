package com.nullpointerbay.turbochat.di;

import android.content.Context;

import com.nullpointerbay.turbochat.TurboChatBaseApplication;
import com.nullpointerbay.turbochat.utils.UrlResolver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TurboChatModule {

    private final TurboChatBaseApplication application;

    public TurboChatModule(TurboChatBaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public UrlResolver provideUrlResolver() {
        return new UrlResolver();
    }
}
