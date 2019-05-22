package com.gengcon.android.fixedassets.module.login.view.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.common.module.http.URL;
import com.gengcon.android.fixedassets.util.Constant;

import androidx.annotation.Nullable;

public class AgreementActivity extends BaseActivity implements View.OnClickListener {

    private WebView mWebView;
    private String url;
    private TextView webTitle;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_URL);
        setContentView(R.layout.activity_agreement);
        webTitle = findViewById(R.id.tv_title_text);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.set_return);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        mWebView = findViewById(R.id.webView);
        mWebView.loadUrl(url);
        final WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
//        webSettings.setBlockNetworkImage(true);
        mWebView.addJavascriptInterface(this, "android");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals(URL.HTTP_HEAD + URL.REGISTER_AGREEMENT)) {
                    webTitle.setText("精臣用户许可协议");
                    webTitle.setTextColor(getResources().getColor(R.color.black_text));
                } else if (url.equals(URL.HTTP_HEAD + URL.SECRET)) {
                    webTitle.setText("精臣隐私保护政策");
                    webTitle.setTextColor(getResources().getColor(R.color.black_text));
                }
            }
        });
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
