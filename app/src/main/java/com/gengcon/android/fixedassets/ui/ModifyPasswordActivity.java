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
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.presenter.ModifyPasswordPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.ModifyPasswordView;

import androidx.annotation.Nullable;

public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener, ModifyPasswordView {

    private EditText oldPasswordEdit, newPasswordEdit, confirmPasswordEdit;
    private Button sureButton;
    private ModifyPasswordPresenter modifyPasswordPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.modifyPassword);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        oldPasswordEdit = findViewById(R.id.oldPasswordEdit);
        newPasswordEdit = findViewById(R.id.newPasswordEdit);
        confirmPasswordEdit = findViewById(R.id.confirmPasswordEdit);
        sureButton = findViewById(R.id.sureButton);
        sureButton.setOnClickListener(this);

        modifyPasswordPresenter = new ModifyPasswordPresenter();
        modifyPasswordPresenter.attachView(this);
        sureButton.setEnabled(false);

        oldPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int number = editable.length();
                if (number > 0) {
                    if (!TextUtils.isEmpty(newPasswordEdit.getText().toString()) && !TextUtils.isEmpty(confirmPasswordEdit.getText().toString())) {
                        sureButton.setEnabled(true);
                    }
                } else {
                    sureButton.setEnabled(false);
                }
            }
        });

        newPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int number = editable.length();
                if (number > 0) {
                    if (!TextUtils.isEmpty(oldPasswordEdit.getText().toString()) && !TextUtils.isEmpty(confirmPasswordEdit.getText().toString())) {
                        sureButton.setEnabled(true);
                    }
                } else {
                    sureButton.setEnabled(false);
                }
            }
        });

        confirmPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int number = editable.length();
                if (number > 0) {
                    if (!TextUtils.isEmpty(newPasswordEdit.getText().toString()) && !TextUtils.isEmpty(oldPasswordEdit.getText().toString())) {
                        sureButton.setEnabled(true);
                    }
                } else {
                    sureButton.setEnabled(false);
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
            case R.id.sureButton:
//                if (TextUtils.isEmpty(oldPasswordEdit.getText().toString())) {
//                    ToastUtils.toastMessage(this, "请输入旧密码");
//                    return;
//                }
//                if (TextUtils.isEmpty(newPasswordEdit.getText().toString())) {
//                    ToastUtils.toastMessage(this, "请输入新密码");
//                    return;
//                }
//                if (TextUtils.isEmpty(confirmPasswordEdit.getText().toString())) {
//                    ToastUtils.toastMessage(this, "请确认新密码");
//                    return;
//                }
                if (newPasswordEdit.getText().toString().length() < 6 || newPasswordEdit.getText().toString().length() > 20) {
                    ToastUtils.toastMessage(this, "密码格式错误（6-20位，可包含数字，字母，符号）");
                    return;
                }
                if (!(newPasswordEdit.getText().toString().equals(confirmPasswordEdit.getText().toString()))) {
                    ToastUtils.toastMessage(this, "两次输入的新密码不一致");
                    return;
                }
                modifyPasswordPresenter.getModifyPassword(oldPasswordEdit.getText().toString(), newPasswordEdit.getText().toString(), confirmPasswordEdit.getText().toString());
                break;
        }
    }

    @Override
    public void modifyPassword() {
        ToastUtils.toastMessage(this, "修改成功");
        SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
        Intent intent = new Intent(this, WebActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.LOGIN);
        startActivity(intent);
    }
}
