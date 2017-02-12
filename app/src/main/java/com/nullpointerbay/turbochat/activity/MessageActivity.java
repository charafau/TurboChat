package com.nullpointerbay.turbochat.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseActivity;
import com.nullpointerbay.turbochat.fragment.MessageFragment;
import com.nullpointerbay.turbochat.model.Team;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends BaseActivity {

    public static final String ARG_TEAM = "arg_team";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MessageFragment messageFragment;
    private FragmentTransaction ft;

    public static void start(Context context, Team team) {
        final Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(ARG_TEAM, team);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        final Team team = getIntent().getExtras().getParcelable(ARG_TEAM);
        inflateFragment(team);
    }

    private void inflateFragment(Team team) {
        if (messageFragment == null) {
            messageFragment = MessageFragment.createInstance(team);
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.root, messageFragment, MessageFragment.TAG);
            ft.commit();
        }
    }
}
