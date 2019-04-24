package com.gengcon.android.fixedassets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.PersonalBean;
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.presenter.PersonalPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.PersonalView;

import androidx.annotation.Nullable;

public class PersonalActivity extends BaseActivity implements View.OnClickListener, PersonalView {

    private TextView userNameView;
    private TextView powerView;
    private TextView companyView;
    private String phoneNo;

    private PersonalPresenter personalPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.personal);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_home);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        userNameView = findViewById(R.id.userNameView);
        powerView = findViewById(R.id.powerView);
        companyView = findViewById(R.id.companyView);
        personalPresenter = new PersonalPresenter();
        personalPresenter.attachView(this);
        personalPresenter.getUserData();
        findViewById(R.id.personalLayout).setOnClickListener(this);
        findViewById(R.id.changePhoneLayout).setOnClickListener(this);
        findViewById(R.id.modifyPasswordLayout).setOnClickListener(this);
        findViewById(R.id.feedbackLayout).setOnClickListener(this);
        findViewById(R.id.aboutUsLayout).setOnClickListener(this);
        findViewById(R.id.loginOutLayout).setOnClickListener(this);
        findViewById(R.id.serviceLayout).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showErrorMsg(int status, String msg) {
        super.showErrorMsg(status, msg);
    }

    @Override
    public void showCodeMsg(String code, String msg) {
        super.showCodeMsg(code, msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.personalLayout:
                Intent personalIntent = new Intent(this, PersonalDataActivity.class);
                startActivity(personalIntent);
                break;
            case R.id.changePhoneLayout:
                if (!TextUtils.isEmpty(phoneNo)) {
                    Intent changePhoneIntent = new Intent(this, ChangePhoneActivity.class);
                    changePhoneIntent.putExtra("phone", phoneNo);
                    startActivity(changePhoneIntent);
                } else {
                    ToastUtils.toastMessage(this, "系统繁忙，请稍后重试！");
                }
                break;
            case R.id.modifyPasswordLayout:
                Intent modifyPasswordIntent = new Intent(this, ModifyPasswordActivity.class);
                startActivity(modifyPasswordIntent);
                break;
            case R.id.feedbackLayout:
                Intent feedbackIntent = new Intent(this, FeedbackActivity.class);
                startActivity(feedbackIntent);
                break;
            case R.id.aboutUsLayout:
                Intent aboutUsIntent = new Intent(this, AboutUsActivity.class);
                startActivity(aboutUsIntent);
                break;
            case R.id.loginOutLayout:
                loginOut();
                break;
            case R.id.serviceLayout:
                Intent serviceIntent = new Intent(this, CustomerServiceActivity.class);
                startActivity(serviceIntent);
                break;
        }
    }

    private void loginOut() {
        SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
        Intent intent = new Intent(this, WebActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.LOGIN);
        startActivity(intent);
    }

    @Override
    public void showPersonalData(PersonalBean data) {
        userNameView.setText(data.getUser_name());
        powerView.setText(data.getRole_name());
        companyView.setText(data.getOrg_name());
        phoneNo = data.getPhone();
    }

}
