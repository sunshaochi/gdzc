package com.gengcon.android.fixedassets.module.user.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.common.module.http.URL;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.PersonalBean;
import com.gengcon.android.fixedassets.module.base.EaseUiHelper;
import com.gengcon.android.fixedassets.module.user.view.PersonalView;
import com.gengcon.android.fixedassets.module.user.presenter.PersonalPresenter;
import com.gengcon.android.fixedassets.module.web.view.WebActivity;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;

import androidx.annotation.Nullable;

public class PersonalDataActivity extends BaseActivity implements View.OnClickListener, PersonalView {

    private TextView nameView, phoneView, companyNameView, industryView, powerView, versionNameView, maxAssetNumView;
    private PersonalPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.personalData);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.loginOutLayout).setOnClickListener(this);
        nameView = findViewById(R.id.nameView);
        phoneView = findViewById(R.id.phoneView);
        companyNameView = findViewById(R.id.companyNameView);
        industryView = findViewById(R.id.industryView);
        powerView = findViewById(R.id.powerView);
        versionNameView = findViewById(R.id.versionNameView);
//        dueTimeView = findViewById(R.id.dueTimeView);
        maxAssetNumView = findViewById(R.id.maxAssetNumView);
//        maxTextView = findViewById(R.id.maxTextView);
        presenter = new PersonalPresenter();
        presenter.attachView(this);
        presenter.getUserData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.loginOutLayout:
                loginOut();
                break;
        }
    }

    @Override
    public void showPersonalData(PersonalBean data) {
        if (!TextUtils.isEmpty(data.getUser_name())) {
            nameView.setText(data.getUser_name());
        }
        if (!TextUtils.isEmpty(data.getPhone())) {
            phoneView.setText(data.getPhone());
        }
        if (!TextUtils.isEmpty(data.getOrg_name())) {
            companyNameView.setText(data.getOrg_name());
        }
        if (!TextUtils.isEmpty(data.getIndustry_name())) {
            industryView.setText(data.getIndustry_name());
        }
        if (!TextUtils.isEmpty(data.getRole_name())) {
            powerView.setText(data.getRole_name());
        }
        if (!TextUtils.isEmpty(data.getVersion_name())) {
            versionNameView.setText(data.getVersion_name());
        }
//        if (!TextUtils.isEmpty(data.getDue_at())) {
//            dueTimeView.setText(data.getDue_at());
//        }
        if (data.getIs_unlimit() == 0) {
            maxAssetNumView.setText(data.getAsset_max_num() + "");
//            maxTextView.setVisibility(View.GONE);
        } else {
            maxAssetNumView.setText("无限制");
//            maxTextView.setVisibility(View.VISIBLE);
        }
    }

    private void loginOut() {
        SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
        SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.USER_ID);
        EaseUiHelper.getInstance().LoginOutEase();
        Intent intent = new Intent(this, WebActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.LOGIN);
        startActivity(intent);
    }
}
