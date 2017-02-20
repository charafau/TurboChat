package com.nullpointerbay.turbochat.di;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

@ForApplication
@Singleton
@Component(modules = {TurboChatModule.class, MockDataModule.class})
public interface TestTurboChatComponent extends TurboChatComponent {

    NetworkBehavior getNetworkBegavior();

    MockRetrofit getMockRetrofit();

}
