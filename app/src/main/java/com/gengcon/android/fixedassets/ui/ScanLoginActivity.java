package com.gengcon.android.fixedassets.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.presenter.ScanLoginPresenter;
import com.gengcon.android.fixedassets.view.ScanLoginView;

public class ScanLoginActivity extends BaseActivity implements View.OnClickListener, ScanLoginView {

    private String uuid;
    private ScanLoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_login);
        uuid = getIntent().getStringExtra("uuid");
        if (TextUtils.isEmpty(uuid)) {
            finish();
            return;
        }
        initView();
        mPresenter = new ScanLoginPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                mPresenter.scanLogin(uuid, 3);
                onBackPressed();
                break;
            case R.id.btn_confirm:
                mPresenter.scanLogin(uuid, 2);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showScanResult(Bean resultBean) {
        finish();
    }
}
