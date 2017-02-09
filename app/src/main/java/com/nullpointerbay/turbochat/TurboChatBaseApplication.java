package com.nullpointerbay.turbochat;

import android.app.Application;

import com.nullpointerbay.turbochat.di.DaggerTurboChatComponent;
import com.nullpointerbay.turbochat.di.TurboChatComponent;
import com.nullpointerbay.turbochat.di.TurboChatModule;

import timber.log.Timber;

public class TurboChatBaseApplication extends Application {

    protected TurboChatComponent turboChatComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        setupDagger();
    }

    protected void setupDagger() {
        turboChatComponent = DaggerTurboChatComponent.builder()
                .turboChatModule(new TurboChatModule(this))
                .build();
        turboChatComponent.inject(this);
    }

    public TurboChatComponent getTurboChatComponent() {
        return turboChatComponent;
    }
}
