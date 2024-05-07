package com.softarena.checagocoffee.Acitivity.WebView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.softarena.checagocoffee.R;

public class News2Activity extends AppCompatActivity {
    WebView webView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news2);
        webView2 = findViewById(R.id.webView2);
        webView2.setWebViewClient(new WebViewClient());

        webView2.loadUrl("https://blockclubchicago.org/");
        WebSettings webSettings = webView2.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
    @Override
    public void onBackPressed() {
        if (webView2.canGoBack())
        {
            webView2.goBack();
        }else
        {
            super.onBackPressed();
        }

    }
}