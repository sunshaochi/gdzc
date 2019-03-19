package com.gengcon.android.fixedassets.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.util.Constant;

import androidx.annotation.Nullable;

public class AgreementActivity extends BaseActivity implements View.OnClickListener {

    private WebView mWebView;
    private String url;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_URL);
        type = getIntent().getIntExtra("agreeType", -1);
        setContentView(R.layout.activity_agreement);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.set_return);
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
