package com.softarena.checagocoffee.Acitivity.WebView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.softarena.checagocoffee.R;

public class News1Activity extends AppCompatActivity {
    WebView webView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news1);
        String url=getIntent().getStringExtra("link");
        if (!url.startsWith("http://") && !url.startsWith("https://")){
            url = "https://" + url;
            }
        webView1 = findViewById(R.id.webView1);
        webView1.setWebViewClient(new WebViewClient());
        webView1.loadUrl(url);
        WebSettings webSettings = webView1.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (webView1.canGoBack())
        {
            webView1.goBack();
        }else
        {
            super.onBackPressed();
        }

    }
}