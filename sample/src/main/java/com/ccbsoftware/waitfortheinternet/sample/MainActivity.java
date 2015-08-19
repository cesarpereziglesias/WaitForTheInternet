package com.ccbsoftware.waitfortheinternet.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ccbsoftware.waitfortheinternet.WaitForTheInternet;

public class MainActivity extends AppCompatActivity {

    protected WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView)findViewById(R.id.wv_browser);
        mWebView.setWebViewClient(new WebViewClient());

        findViewById(R.id.btn_load_webview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaitForTheInternet
                        .with(MainActivity.this)
                        .action(new WaitForTheInternet.OnInternetAction() {

                            @Override
                            public void onInternet() {
                                loadWebView();
                            }

                        })
                        .execute();
            }
        });

    }

    protected void loadWebView() {
        mWebView.loadUrl("http://www.google.com");
    }

}
