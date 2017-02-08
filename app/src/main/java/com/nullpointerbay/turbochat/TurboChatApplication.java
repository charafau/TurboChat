package com.nullpointerbay.turbochat;

import android.app.Application;

import com.nullpointerbay.turbochat.di.DaggerTurboChatComponent;
import com.nullpointerbay.turbochat.di.TurboChatComponent;
import com.nullpointerbay.turbochat.di.TurboChatModule;

public class TurboChatApplication extends Application {

    private TurboChatComponent turboChatComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDagger();
    }

    private void setupDagger() {
        turboChatComponent = DaggerTurboChatComponent.builder()
                .turboChatModule(new TurboChatModule(this))
                .build();
        turboChatComponent.inject(this);
    }
}
