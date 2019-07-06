package com.matt.android.mynews.controllers.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.matt.android.mynews.R;
import com.matt.android.mynews.controllers.fragments.tabs.BaseFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    // Web View Layout
    @BindView(R.id.web_view)
    WebView webView;
    WebViewClient webViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        //Initialize ButterKnife
        ButterKnife.bind(this);
        //Configure Toolbar
        configureToolbar();
        //Configure WebView
        this.setWebView();
    }

    /**
     * Instantiate WebView + Get url and load it to it
     */
    private void setWebView() {
        webViewClient = new WebViewClient();
        webView.setWebViewClient(webViewClient);
        String url=getIntent().getStringExtra(BaseFragment.BUNDLE_URL);
        webView.loadUrl(url);
    }

    /**
     * Configure toolbar + add back button
     */
    private void configureToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbar);

        //This is for back button
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Method called to finish current Activity lifecycle and go back to previous activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
