package com.algebralineal.tpalgebralineal;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InformationActivity extends AppCompatActivity {

    String url = "https://docs.google.com/document/d/1E1zG1_6z-F8Ovy3l52jKirfcXFohW4TOcRP_1RnuEXU/edit?usp=sharing";
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        WebView wb = (WebView) findViewById(R.id.wb);
        wb.setWebViewClient(new MyWebViewClient());
        WebSettings sett = wb.getSettings();
        sett.setJavaScriptEnabled(true);
        wb.loadUrl(url);
    }
    private class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }
}
