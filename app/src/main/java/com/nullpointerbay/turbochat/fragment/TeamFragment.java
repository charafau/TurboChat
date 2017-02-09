package com.nullpointerbay.turbochat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseFragment;

public class TeamFragment extends BaseFragment {

    public static final String TAG = TeamFragment.class.getSimpleName();

    public static TeamFragment createInstance() {
        return new TeamFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_team, container, false);

        return view;
    }
}
