package com.gengcon.android.fixedassets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.presenter.PhoneCodePresenter;
import com.gengcon.android.fixedassets.util.CacheActivity;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.PhoneCodeView;

import androidx.annotation.Nullable;

public class ForgetPwdFirstActivity extends BaseActivity implements View.OnClickListener, PhoneCodeView {

    private EditText phoneView;
    private Button nextButton;
    private String forgetPhone;
    private PhoneCodePresenter phoneCodePresenter;
    private Button clearButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_first);
        if (!CacheActivity.activityList.contains(ForgetPwdFirstActivity.this)) {
            CacheActivity.activityList.add(ForgetPwdFirstActivity.this);
        }
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.set_return);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        phoneView = findViewById(R.id.phoneView);
        nextButton = findViewById(R.id.nextButton);
        clearButton = findViewById(R.id.clearButton);
        clearButton.setVisibility(View.GONE);
        clearButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        nextButton.setEnabled(false);
        phoneCodePresenter = new PhoneCodePresenter();
        phoneCodePresenter.attachView(this);
        phoneView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0) {
                    nextButton.setEnabled(true);
                    clearButton.setVisibility(View.VISIBLE);
                } else {
                    nextButton.setEnabled(false);
                    clearButton.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.clearButton:
                phoneView.setText("");
                break;
            case R.id.nextButton:
                forgetPhone = phoneView.getText().toString();
                if (TextUtils.isEmpty(forgetPhone)) {
                    ToastUtils.toastMessage(this, "请输入手机号");
                    return;
                }
                if (forgetPhone.length() == 11) {
                    long currentTime = System.currentTimeMillis();
                    long time = (long) SharedPreferencesUtils.getInstance().getParam("forgetPhoneCodeTime", -1l);
                    if (currentTime - time > 1000 * 60) {
                        phoneCodePresenter.getPhoneCode(forgetPhone, "2");
                    } else {
                        Intent intent = new Intent(this, ForgetSecondActivity.class);
                        intent.putExtra("forgetPhone", forgetPhone);
                        startActivity(intent);
                    }
                } else {
                    ToastUtils.toastMessage(this, "手机号格式不正确");
                }
                break;
        }
    }

    @Override
    public void showPhoneCode() {
        long currentTime = System.currentTimeMillis();
        SharedPreferencesUtils.getInstance().setParam("forgetPhoneCodeTime", currentTime);
        Intent intent = new Intent(this, ForgetSecondActivity.class);
        intent.putExtra("forgetPhone", forgetPhone);
        startActivity(intent);
    }
}
