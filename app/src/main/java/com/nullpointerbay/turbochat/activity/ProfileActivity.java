package com.nullpointerbay.turbochat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseActivity;
import com.nullpointerbay.turbochat.fragment.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity {

    public static final String ARG_USER = "arg_user";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ProfileFragment profileFragment;

    public static void start(Context context, String userNick) {
        final Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(ARG_USER, userNick);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);

        String userNick = getIntent().getExtras().getString(ARG_USER);
        inflateFragment(userNick);
        setSupportActionBar(toolbar);
        final ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(userNick);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void inflateFragment(String userNick) {
        if (profileFragment == null) {
            profileFragment = ProfileFragment.createInstance(userNick);
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.root, profileFragment, ProfileFragment.TAG);
            ft.commit();
        }
    }
}
