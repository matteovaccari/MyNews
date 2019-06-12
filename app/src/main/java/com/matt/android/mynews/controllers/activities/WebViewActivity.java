package com.matt.android.mynews.controllers.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.matt.android.mynews.R;
import com.matt.android.mynews.controllers.fragments.BaseFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    // Web View Layout
    @BindView(R.id.webview)
    WebView mWebView;
    WebViewClient mWebViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        // Initialize ButterKnife
        ButterKnife.bind(this);
        // Toolbar
        configureToolbar();
        // Configure webview
        this.getWebView();
    }

    /**
     * Create the webView
     */
    private void getWebView() {
        mWebViewClient = new WebViewClient();
        mWebView.setWebViewClient(mWebViewClient);
        String url=getIntent().getStringExtra(BaseFragment.BUNDLE_URL);
        mWebView.loadUrl(url);
    }

    /**
     * Add Toolbar
     */
    private void configureToolbar(){
        //Get the toolbar view inside the activity layout
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        // Set the Toolbar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the up button
        assert ab != null;
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }
}
