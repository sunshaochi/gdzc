package com.gengcon.android.fixedassets.module.base;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;

import androidx.annotation.Nullable;

public class CustomerServiceActivity extends BaseActivity implements View.OnClickListener {

    private WebView mWebView;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = "https://kefu.easemob.com/webim/im.html?configId=d467f9bd-f7bc-4c92-a083-6dcfc114de88";
        setContentView(R.layout.activity_customer_service);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText("在线客服");
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        mWebView = findViewById(R.id.webView);
        mWebView.loadUrl(url);
        final WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showErrorMsg(int status, String msg) {
        super.showErrorMsg(status, msg);
    }

    @Override
    public void showCodeMsg(String code, String msg) {
        super.showCodeMsg(code, msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
        }
    }
}
