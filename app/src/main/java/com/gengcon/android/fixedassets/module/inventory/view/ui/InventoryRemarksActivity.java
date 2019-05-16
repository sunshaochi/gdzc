package com.gengcon.android.fixedassets.module.inventory.view.ui;

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
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.module.base.GApplication;
import com.gengcon.android.fixedassets.module.greendao.InventoryBean;
import com.gengcon.android.fixedassets.module.greendao.InventoryBeanDao;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;

import androidx.annotation.Nullable;

public class InventoryRemarksActivity extends BaseActivity implements View.OnClickListener {

    private EditText remarksEdit;
    private TextView textCount;
    private Button sureButton;
    private static int MAX_COUNT = 200;
    private InventoryBeanDao inventoryBeanDao;
    private String remarks;
    private String pd_no;
    private String user_id;
    private InventoryBean inventory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_remarks);
        inventoryBeanDao = GApplication.getDaoSession().getInventoryBeanDao();
        pd_no = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID);
        user_id = (String) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.USER_ID, "");
        inventory = inventoryBeanDao.queryBuilder()
                .where(InventoryBeanDao.Properties.User_id.eq(user_id))
                .where(InventoryBeanDao.Properties.Pd_no.eq(pd_no)).unique();
        remarks = inventory.getRemarks();
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.inventory_remarks);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        textCount = findViewById(R.id.textCount);
        remarksEdit = findViewById(R.id.remarksEdit);
        sureButton = findViewById(R.id.sureButton);
        sureButton.setOnClickListener(this);
        textCount.setText(0 + "/" + MAX_COUNT);
        if (!TextUtils.isEmpty(remarks)) {
            remarksEdit.setText(remarks);
        }
        showCharNumber(MAX_COUNT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sureButton:
                if (TextUtils.isEmpty(remarksEdit.getText().toString())) {
                    inventory.setRemarks("");
                } else {
                    inventory.setRemarks(remarksEdit.getText().toString());
                }
                inventoryBeanDao.update(inventory);
                finish();
                break;
            case R.id.iv_title_left:
                onBackPressed();
                break;
        }
    }

    private void showCharNumber(final int maxNumber) {
        remarksEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = s.length();
                textCount.setText(number + "/" + maxNumber);
            }
        });
    }

}
