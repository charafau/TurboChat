package com.nullpointerbay.turbochat.di;

import javax.inject.Singleton;

import dagger.Component;

@ForApplication
@Singleton
@Component(modules = {TurboChatModule.class, MockDataModule.class})
public interface TestTurboChatComponent extends TurboChatComponent {
}
