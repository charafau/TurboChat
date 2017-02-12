package com.nullpointerbay.turbochat.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseActivity;
import com.nullpointerbay.turbochat.fragment.TeamFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    TeamFragment teamFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        inflateFragment();
        final ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.app_name);
        supportActionBar.setSubtitle(R.string.activity_teams);
    }

    private void inflateFragment() {
        if (teamFragment == null) {
            teamFragment = TeamFragment.createInstance();
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.root, teamFragment, TeamFragment.TAG);
            ft.commit();
        }

    }
}
