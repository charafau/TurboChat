package com.nullpointerbay.turbochat.di;

import android.content.Context;

import com.nullpointerbay.turbochat.TurboChatApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TurboChatModule {

    private final TurboChatApplication application;

    public TurboChatModule(TurboChatApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }
}
