package com.gengcon.android.fixedassets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.presenter.ForgetPwdPresenter;
import com.gengcon.android.fixedassets.util.CacheActivity;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.ForgetPwdView;

import androidx.annotation.Nullable;

public class SetPasswordActivity extends BaseActivity implements View.OnClickListener, ForgetPwdView {

    private EditText passwordEdit;
    private Button nextButton;
    private String phoneNumber;
    private String type;
    private String secret_key;
    private Button hidePwdBtn;
    private boolean isHidePwd = false;
    private ForgetPwdPresenter forgetPwdPresenter;
    private Button clearButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        if (!CacheActivity.activityList.contains(SetPasswordActivity.this)) {
            CacheActivity.activityList.add(SetPasswordActivity.this);
        }
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.set_return);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        initIntent(getIntent());
        hidePwdBtn = findViewById(R.id.hidePwdBtn);
        hidePwdBtn.setOnClickListener(this);
        passwordEdit = findViewById(R.id.passwordEdit);
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        clearButton = findViewById(R.id.clearButton);
        clearButton.setVisibility(View.GONE);
        clearButton.setOnClickListener(this);
        nextButton.setEnabled(false);
        passwordEdit.addTextChangedListener(new TextWatcher() {
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

    private void initIntent(Intent intent) {
        phoneNumber = intent.getStringExtra("phoneNumber");
        type = intent.getStringExtra("type");
        if (type.equals("forget")) {
            secret_key = intent.getStringExtra("secret_key");
            forgetPwdPresenter = new ForgetPwdPresenter();
            forgetPwdPresenter.attachView(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.hidePwdBtn:
                isHidePwd = !isHidePwd;
                if (isHidePwd) {
                    passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordEdit.setSelection(passwordEdit.getText().length());
                } else {
                    passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordEdit.setSelection(passwordEdit.getText().length());
                }
                hidePwdBtn.setBackgroundResource(isHidePwd ? R.drawable.ic_hide_password : R.drawable.ic_display_password);
                if (!TextUtils.isEmpty(passwordEdit.getText().toString())) {
                    nextButton.setEnabled(true);
                } else {
                    nextButton.setEnabled(false);
                }
                break;

            case R.id.clearButton:
                passwordEdit.setText("");
                break;
            case R.id.nextButton:
                if (passwordEdit.getText().toString().length() < 6 || passwordEdit.getText().toString().length() > 20) {
                    ToastUtils.toastMessage(this, "密码格式错误（6-20位，可包含数字，字母，符号）");
                    return;
                }
                if (type.equals("forget")) {
                    forgetPwdPresenter.getForgetPwd(secret_key, passwordEdit.getText().toString(), passwordEdit.getText().toString());
                } else {
                    Intent intent = new Intent(this, RegisterLastActivity.class);
                    intent.putExtra("registerPhone", phoneNumber);
                    intent.putExtra("pwd", passwordEdit.getText().toString());
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void setPwdSuccess() {
        SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
        Intent intent = new Intent(SetPasswordActivity.this, WebActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.LOGIN);
        startActivity(intent);
        ToastUtils.toastMessage(this, "密码修改成功");
    }
}
