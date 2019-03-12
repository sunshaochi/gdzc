package com.gengcon.android.fixedassets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.util.ToastUtils;

import androidx.annotation.Nullable;

public class ChangePhoneThirdActivity extends BaseActivity implements View.OnClickListener {

    private EditText newPhoneView;
    private Button nextButton;
    private String newPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_third);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.change_phone);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        newPhoneView = findViewById(R.id.newPhoneView);
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.nextButton:
                newPhone = newPhoneView.getText().toString();
                if (TextUtils.isEmpty(newPhone)) {
                    ToastUtils.toastMessage(this, "请输入新手机号");
                    return;
                }
                if (newPhone.length() == 11) {
                    Intent intent = new Intent(this, ChangePhoneFourthActivity.class);
                    intent.putExtra("newPhone", newPhone);
                    startActivity(intent);
                } else {
                    ToastUtils.toastMessage(this, "手机号格式不正确");
                }
                break;
        }
    }
}
