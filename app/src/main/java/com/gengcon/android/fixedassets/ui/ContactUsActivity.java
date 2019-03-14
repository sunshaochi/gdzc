package com.gengcon.android.fixedassets.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.ContactUs;
import com.gengcon.android.fixedassets.presenter.ContactUsPresenter;
import com.gengcon.android.fixedassets.view.ContactUsView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ContactUsActivity extends BaseActivity implements View.OnClickListener, ContactUsView {

    private TextView callPhoneView;
    private TextView phoneNoView;
    private TextView emailTextView;
    private ContactUsPresenter presenter;
    private String officialTel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.contact_us);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        callPhoneView = findViewById(R.id.callPhoneView);
        callPhoneView.setOnClickListener(this);
        phoneNoView = findViewById(R.id.phoneNoView);
        emailTextView = findViewById(R.id.emailTextView);
        presenter = new ContactUsPresenter();
        presenter.attachView(this);
        presenter.getContactUs();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.callPhoneView:
                if (!TextUtils.isEmpty(officialTel)) {
                    onCall(officialTel);
                }
                break;
        }
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;

    public void onCall(String mobile) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CALL_PHONE
                }, REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                callPhone(mobile);
            }
        } else {
            callPhone(mobile);
        }

    }

    //动态权限申请后处理
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted callDirectly(mobile);
                } else {
                    // Permission Denied Toast.makeText(MainActivity.this,"CALL_PHONE Denied", Toast.LENGTH_SHORT) .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void showData(ContactUs contactUs) {
        officialTel = contactUs.getOfficial_tel();
        phoneNoView.setText(contactUs.getCsd_tel());
        emailTextView.setText(contactUs.getEmail());
        callPhoneView.setText(contactUs.getOfficial_tel());
    }
}
