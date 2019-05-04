package com.gengcon.android.fixedassets.module.user.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.base.BaseActivity;

import androidx.annotation.Nullable;

public class OrgSettingActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_setting);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.org_setting);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.orgLayout).setOnClickListener(this);
        findViewById(R.id.staffManagerLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.orgLayout:
                Intent intent = new Intent(this, OrgSettingSecondActivity.class);
                startActivity(intent);
                break;
            case R.id.staffManagerLayout:
                Intent staffIntent = new Intent(this, StaffMangerActivity.class);
                startActivity(staffIntent);
                break;
        }
    }

}
