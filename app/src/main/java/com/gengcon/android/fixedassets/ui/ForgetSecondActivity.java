package com.gengcon.android.fixedassets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.ForgetPwd;
import com.gengcon.android.fixedassets.presenter.CheckPhonePresenter;
import com.gengcon.android.fixedassets.presenter.PhoneCodePresenter;
import com.gengcon.android.fixedassets.util.CacheActivity;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.view.CheckPhoneCodeView;
import com.gengcon.android.fixedassets.view.PhoneCodeView;
import com.gengcon.android.fixedassets.widget.PhoneCodeLayout;

import androidx.annotation.Nullable;

public class ForgetSecondActivity extends BaseActivity implements View.OnClickListener, PhoneCodeView, CheckPhoneCodeView, PhoneCodeLayout.OnInputFinishListener {

    private String forgetPhone;
    private PhoneCodePresenter phoneCodePresenter;
    private TextView phoneView;
    private LinearLayout timeLayout;
    private TextView timeView;
    private TextView againSendView;
    private MyCountDownTimer myCountDownTimer;
    private PhoneCodeLayout phoneCodeLayout;
    private CheckPhonePresenter checkPhonePresenter;
    private String secret_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_second);
        if (!CacheActivity.activityList.contains(ForgetSecondActivity.this)) {
            CacheActivity.activityList.add(ForgetSecondActivity.this);
        }
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.set_return);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        phoneView = findViewById(R.id.phoneView);
        timeView = findViewById(R.id.timeView);
        timeLayout = findViewById(R.id.timeLayout);
        againSendView = findViewById(R.id.againSendView);
        forgetPhone = getIntent().getStringExtra("forgetPhone");
        setPhoneView(forgetPhone);
        phoneCodePresenter = new PhoneCodePresenter();
        phoneCodePresenter.attachView(this);
        checkPhonePresenter = new CheckPhonePresenter();
        checkPhonePresenter.attachView(this);
        myCountDownTimer = new MyCountDownTimer(60000, 1000);
        myCountDownTimer.start();
        againSendView.setOnClickListener(this);
        phoneCodeLayout = findViewById(R.id.codeView);
        phoneCodeLayout.setmInputListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.againSendView:
                phoneCodePresenter.getPhoneCode(forgetPhone, "2");
                againSendView.setVisibility(View.GONE);
                myCountDownTimer.start();
                break;
        }
    }

    private void setPhoneView(String phone) {
        String first = phone.substring(0, 3);
        String second = phone.substring(3, 7);
        String third = phone.substring(7, phone.length());
        phoneView.setText("+86 " + first + " " + second + " " + third);
    }

    @Override
    public void onFinish(String code) {
        checkPhonePresenter.getResetPwdVerify(forgetPhone, code);
    }

    @Override
    public void checkPhoneCode() {

    }

    @Override
    public void checkForgetPhoneCode(ForgetPwd forgetPwd) {
        secret_key = forgetPwd.getSecret_key();
        Intent intent = new Intent(this, SetPasswordActivity.class);
        intent.putExtra("phoneNumber", forgetPhone);
        intent.putExtra("type", "forget");
        intent.putExtra("secret_key", secret_key);
        startActivity(intent);
    }

    @Override
    public void showPhoneCode() {
        long currentTime = System.currentTimeMillis();
        SharedPreferencesUtils.getInstance().setParam("forgetPhoneCodeTime", currentTime);
    }

    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            timeLayout.setVisibility(View.VISIBLE);
            againSendView.setVisibility(View.GONE);
            if (l / 1000 > 9) {
                timeView.setText("00:" + l / 1000);
            } else {
                timeView.setText("00:0" + l / 1000);
            }

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            timeLayout.setVisibility(View.GONE);
            againSendView.setVisibility(View.VISIBLE);
        }
    }
}
