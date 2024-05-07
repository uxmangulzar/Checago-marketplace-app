package com.softarena.checagocoffee.Acitivity.WebView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.softarena.checagocoffee.R;

public class News3Activity extends AppCompatActivity {
    WebView webView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news3);
        webView3 = findViewById(R.id.webView3);
        webView3.setWebViewClient(new WebViewClient());
        webView3.loadUrl("https://chicagodefender.com/");
        WebSettings webSettings = webView3.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
    @Override
    public void onBackPressed() {
        if (webView3.canGoBack())
        {
            webView3.goBack();
        }else
        {
            super.onBackPressed();
        }

    }
}