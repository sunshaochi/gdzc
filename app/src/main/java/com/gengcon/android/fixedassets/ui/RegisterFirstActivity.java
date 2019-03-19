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
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.presenter.PhoneCodePresenter;
import com.gengcon.android.fixedassets.util.CacheActivity;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.PhoneCodeView;

import androidx.annotation.Nullable;

public class RegisterFirstActivity extends BaseActivity implements View.OnClickListener, PhoneCodeView {

    private EditText phoneView;
    private Button nextButton;
    private String newPhone;
    private ImageView choiceView;
    private boolean isChoice = false;
    private PhoneCodePresenter phoneCodePresenter;
    private Button clearButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);
        if (!CacheActivity.activityList.contains(RegisterFirstActivity.this)) {
            CacheActivity.activityList.add(RegisterFirstActivity.this);
        }
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.set_return);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.permitView).setOnClickListener(this);
        findViewById(R.id.protectView).setOnClickListener(this);
        choiceView = findViewById(R.id.choiceView);
        choiceView.setOnClickListener(this);
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
                if (isChoice && length > 0) {
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
            case R.id.choiceView:
                isChoice = !isChoice;
                choiceView.setImageResource(isChoice ? R.drawable.ic_select : R.drawable.ic_unchecked);
                if (!TextUtils.isEmpty(phoneView.getText().toString()) && isChoice) {
                    nextButton.setEnabled(true);
                } else {
                    nextButton.setEnabled(false);
                }
                break;
            case R.id.permitView:
                Intent webIntent = new Intent(RegisterFirstActivity.this, AgreementActivity.class);
                webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.REGISTER_AGREEMENT + 1);
                startActivity(webIntent);
                break;
            case R.id.clearButton:
                phoneView.setText("");
                break;
            case R.id.protectView:
                Intent protectIntent = new Intent(RegisterFirstActivity.this, AgreementActivity.class);
                protectIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.REGISTER_AGREEMENT + 2);
                startActivity(protectIntent);
                break;
            case R.id.nextButton:
                newPhone = phoneView.getText().toString();
                if (TextUtils.isEmpty(newPhone)) {
                    ToastUtils.toastMessage(this, "请输入新手机号");
                    return;
                }
                if (newPhone.length() == 11) {
                    long currentTime = System.currentTimeMillis();
                    long time = (long) SharedPreferencesUtils.getInstance().getParam("registerPhoneCodeTime", -1l);
                    if (currentTime - time > 1000 * 60) {
                        phoneCodePresenter.getPhoneCode(newPhone, "1");
                    } else {
                        Intent intent = new Intent(this, RegisterSecondActivity.class);
                        intent.putExtra("registerPhone", newPhone);
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
        SharedPreferencesUtils.getInstance().setParam("registerPhoneCodeTime", currentTime);
        Intent intent = new Intent(this, RegisterSecondActivity.class);
        intent.putExtra("registerPhone", newPhone);
        startActivity(intent);
    }
}
