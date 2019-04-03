package com.gengcon.android.fixedassets.module.user.view.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.PersonalBean;
import com.gengcon.android.fixedassets.module.user.view.PersonalView;
import com.gengcon.android.fixedassets.module.user.presenter.PersonalPresenter;

import androidx.annotation.Nullable;

public class PersonalDataActivity extends BaseActivity implements View.OnClickListener, PersonalView {

    private TextView nameView, phoneView, companyView, industryView, powerView;
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
        nameView = findViewById(R.id.nameView);
        phoneView = findViewById(R.id.phoneView);
        companyView = findViewById(R.id.companyView);
        industryView = findViewById(R.id.industryView);
        powerView = findViewById(R.id.powerView);
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
            companyView.setText(data.getOrg_name());
        }
        if (!TextUtils.isEmpty(data.getIndustry_name())) {
            industryView.setText(data.getIndustry_name());
        }
        if (!TextUtils.isEmpty(data.getRole_name())) {
            powerView.setText(data.getRole_name());
        }

    }
}
