package com.nullpointerbay.turbochat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseFragment;
import com.nullpointerbay.turbochat.di.DaggerViewComponent;
import com.nullpointerbay.turbochat.di.TurboChatComponent;
import com.nullpointerbay.turbochat.di.ViewModule;
import com.nullpointerbay.turbochat.viewmodel.TeamViewModel;

import javax.inject.Inject;

public class TeamFragment extends BaseFragment {

    public static final String TAG = TeamFragment.class.getSimpleName();

    @Inject
    TeamViewModel teamViewModel;

    public static TeamFragment createInstance() {
        return new TeamFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_team, container, false);

        teamViewModel.test();

        return view;
    }

    @Override
    protected void setup(TurboChatComponent component) {
        DaggerViewComponent.builder()
                .turboChatComponent(component)
                .viewModule(new ViewModule())
                .build()
                .inject(this);

    }
}
