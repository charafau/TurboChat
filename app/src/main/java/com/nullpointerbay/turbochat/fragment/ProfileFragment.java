package com.nullpointerbay.turbochat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseFragment;
import com.nullpointerbay.turbochat.di.TurboChatComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by charafau on 2017/02/13.
 */

public class ProfileFragment extends BaseFragment {

    public static final String ARG_USER = "arg_user";
    public static final String TAG = ProfileFragment.class.getSimpleName();
    @BindView(R.id.txt_user)
    TextView txtUser;

    public static ProfileFragment createInstance(String user) {
        final Bundle args = new Bundle();
        args.putString(ARG_USER, user);
        final ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(args);
        return profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        String userNick = getArguments().getString(ARG_USER);
        txtUser.setText(userNick);

        return view;
    }

    @Override
    protected void setup(TurboChatComponent component) {

    }

    @Override
    protected void bind() {

    }
}
