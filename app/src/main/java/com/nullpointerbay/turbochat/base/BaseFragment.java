package com.nullpointerbay.turbochat.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.nullpointerbay.turbochat.TurboChatApplication;
import com.nullpointerbay.turbochat.di.TurboChatComponent;

public abstract class BaseFragment extends Fragment {

    protected abstract void setup(TurboChatComponent component);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup(((TurboChatApplication)getActivity().getApplication()).getTurboChatComponent());
    }


}
