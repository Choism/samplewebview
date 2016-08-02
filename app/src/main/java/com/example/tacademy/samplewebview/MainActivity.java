package com.example.tacademy.samplewebview;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    WebView webView;
    EditText urlView;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_view);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);//새로고침 보여지는 색상 변경
        webView = (WebView) findViewById(R.id.webView);
        urlView = (EditText) findViewById(R.id.edit_url);

        //refreshLayout 선언
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String url = urlView.getText().toString();
                webView.loadUrl(url);
            }
        });


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               if(url.startsWith("market://")) {
                   Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                   startActivity(intent);
                   return true;
               }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i("MainActivity", "progress : " + newProgress);

                if(newProgress == 100){  //새로고침 할때 100 완료시 종료 하게 선언
                    refreshLayout.setRefreshing(false);
                }
            }
        });

        String url = null;
        Uri uri = getIntent().getData();
        String action = getIntent().getAction();
        if (action != null && action.equals(Intent.ACTION_VIEW)) {
            url = uri.toString();
        }
        if (url == null) {
            url = "http://www.google.com";
        }
        urlView.setText(url);
        webView.loadUrl(url);


        Button btn = (Button)findViewById(R.id.btn_go);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlView.getText().toString();
                if (!TextUtils.isEmpty(url)) {
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "http://" + url;
                    }
                    urlView.setText(url);
                    webView.loadUrl(url);
                }
            }
        });

        btn = (Button)findViewById(R.id.btn_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            }
        });

        btn = (Button)findViewById(R.id.btn_forward);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoForward()) {
                    webView.goForward();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.pauseTimers();
    }
}
