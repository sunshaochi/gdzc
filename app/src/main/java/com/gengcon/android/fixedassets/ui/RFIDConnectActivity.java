package com.gengcon.android.fixedassets.ui;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.util.RFIDUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;

public class RFIDConnectActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtAddress, mEtAntenna, mEtDeviceType;
    private GApplication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid_connect);
        Application application1 = getApplication();
        application = (GApplication) application1;
        initView();
    }

    @Override
    protected void initView() {
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.rfid_connect);

        mEtAddress = findViewById(R.id.et_address);
        mEtAntenna = findViewById(R.id.et_antenna);
        mEtDeviceType = findViewById(R.id.et_device_type);

        mEtAddress.setText(RFIDUtils.getDevPath());
        mEtAntenna.setText(R.string.ont_antenna);
        mEtDeviceType.setText("IDTA");
        mEtAddress.setFocusable(false);
        mEtAntenna.setFocusable(false);
        mEtDeviceType.setFocusable(false);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.btn_connect).setOnClickListener(this);
        findViewById(R.id.btn_disconnect).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.btn_connect:
                boolean isConnect = RFIDUtils.connect(application);
                ToastUtils.toastMessage(this, isConnect ? getString(R.string.connect) + getString(R.string.success) : getString(R.string.connect) + getString(R.string.fail));
                if (isConnect) {
                    onBackPressed();
                }
                break;
            case R.id.btn_disconnect:
                RFIDUtils.disconnect(application);
                ToastUtils.toastMessage(this, "断开成功");
                break;
        }
    }
}
