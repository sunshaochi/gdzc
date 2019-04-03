package com.gengcon.android.fixedassets.module.user.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.base.BaseActivity;

import androidx.annotation.Nullable;

public class ChangePhoneActivity extends BaseActivity implements View.OnClickListener {

    private TextView phoneView;
    private Button changePhoneButton;
    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.change_phone);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        phoneView = findViewById(R.id.phoneView);
        changePhoneButton = findViewById(R.id.changePhoneButton);
        changePhoneButton.setOnClickListener(this);
        phone = getIntent().getStringExtra("phone");
        if (!TextUtils.isEmpty(phone)) {
            phoneView.setText(phone);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.changePhoneButton:
                Intent intent = new Intent(this, ChangePhoneSecondActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
                break;
        }
    }
}
