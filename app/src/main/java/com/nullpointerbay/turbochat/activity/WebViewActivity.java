package com.nullpointerbay.turbochat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebViewActivity extends BaseActivity {

    public static final String ARG_URL = "arg_url";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView webview;

    public static void start(Context context, String url) {
        final Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(ARG_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        final String url = getIntent().getExtras().getString(ARG_URL, "");
        toolbar.setTitle(url);
        setSupportActionBar(toolbar);
        final ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                toolbar.setTitle(view.getTitle());
                toolbar.setSubtitle(url);
            }
        });
        webview.loadUrl(url);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
