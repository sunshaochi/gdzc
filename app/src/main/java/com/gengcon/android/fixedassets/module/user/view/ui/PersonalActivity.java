package com.gengcon.android.fixedassets.module.user.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.PersonalBean;
import com.gengcon.android.fixedassets.module.base.EaseUiHelper;
import com.gengcon.android.fixedassets.module.user.view.PersonalView;
import com.gengcon.android.fixedassets.module.user.presenter.PersonalPresenter;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;

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
        findViewById(R.id.orgLayout).setOnClickListener(this);
        findViewById(R.id.personalLayout).setOnClickListener(this);
        findViewById(R.id.changePhoneLayout).setOnClickListener(this);
        findViewById(R.id.modifyPasswordLayout).setOnClickListener(this);
        findViewById(R.id.feedbackLayout).setOnClickListener(this);
        findViewById(R.id.aboutUsLayout).setOnClickListener(this);
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
            case R.id.orgLayout:
                Intent intent = new Intent(this, OrgSettingActivity.class);
                startActivity(intent);
                break;
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
            case R.id.serviceLayout:
//                Intent serviceIntent = new Intent(this, CustomerServiceActivity.class);
//                startActivity(serviceIntent);
                //注册环信为保证用户唯一性，这里用用户的user_id作为唯一的标识，注册环信username用fixed+user_id 密码用user_id
                String user_id = (String) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.USER_ID, "");
                if (!TextUtils.isEmpty(user_id)) {
                    EaseUiHelper.getInstance().RegistEasemo("fixed" + user_id, user_id);
                }
                break;
        }
    }

    @Override
    public void showPersonalData(PersonalBean data) {
        userNameView.setText(data.getUser_name());
        powerView.setText(data.getRole_name());
        companyView.setText(data.getOrg_name());
        phoneNo = data.getPhone();
    }

}
