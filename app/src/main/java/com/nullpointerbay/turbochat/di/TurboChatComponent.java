package com.nullpointerbay.turbochat.di;

import android.content.Context;

import com.nullpointerbay.turbochat.TurboChatApplication;

import javax.inject.Singleton;

import dagger.Component;

@ForApplication
@Singleton
@Component(modules = TurboChatModule.class)
public interface TurboChatComponent {

    void inject(TurboChatApplication application);

    Context getContext();

}
