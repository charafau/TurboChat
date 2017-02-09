package com.nullpointerbay.turbochat;

import com.nullpointerbay.turbochat.di.DaggerTurboChatComponent;
import com.nullpointerbay.turbochat.di.TurboChatModule;

public class TurboChatApplication extends TurboChatBaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void setupDagger() {
        turboChatComponent = DaggerTurboChatComponent.builder()
                .turboChatModule(new TurboChatModule(this))
                .build();
        turboChatComponent.inject(this);
    }
}
