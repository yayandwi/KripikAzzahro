package com.yayan.kripikazzahro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import android.webkit.WebViewClient;


public class Lokasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi);

        WebView web = (WebView) findViewById(R.id.webViewApp1);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient() {

            public boolean shoulOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        web.loadUrl("https://www.google.co.id/maps/place/7%C2%B050'08.5%22S+110%C2%B009'53.7%22E/@-7.8356804,110.1561729,15z/data=!3m1!4b1!4m12!1m6!3m5!1s0x2e7afae815661269:0x9d7519b8321e673e!2sBalai+Desa+Sendangsari!8m2!3d-7.8357014!4d110.1656443!3m4!1s0x0:0x0!8m2!3d-7.8356806!4d110.1649277?hl=id"
                );
    }
}
