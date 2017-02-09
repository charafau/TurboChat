package com.nullpointerbay.turbochat.di;

import android.content.Context;

import com.nullpointerbay.turbochat.TurboChatBaseApplication;

import javax.inject.Singleton;

import dagger.Component;

@ForApplication
@Singleton
@Component(modules = {TurboChatModule.class, DataModule.class})
public interface TurboChatComponent {

    void inject(TurboChatBaseApplication application);

    Context getContext();

}
