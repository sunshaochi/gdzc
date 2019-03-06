package com.gengcon.android.fixedassets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.presenter.ApprovalRejectPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.ApprovalRejectView;

import androidx.annotation.Nullable;

public class RejectActivity extends BaseActivity implements View.OnClickListener, ApprovalRejectView {

    private EditText textReason;
    private TextView textCount;
    private Button sureButton;
    private String doc_no;
    private ApprovalRejectPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject);
        presenter = new ApprovalRejectPresenter();
        presenter.attachView(this);
        initIntent(getIntent());
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        textCount = findViewById(R.id.textCount);
        textReason = findViewById(R.id.textReason);
        sureButton = findViewById(R.id.sureButton);
        sureButton.setOnClickListener(this);
        textCount.setText(0 + "/" + 40);
        showCharNumber(40);
    }

    private void initIntent(Intent intent) {
        doc_no = intent.getStringExtra(Constant.INTENT_EXTRA_KEY_APPROVAL_ID);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sureButton:
                if (TextUtils.isEmpty(textReason.getText().toString())) {
                    ToastUtils.toastMessage(this, "请填写驳回原因");
                } else {
                    presenter.getAuditSave(doc_no, 2, textReason.getText().toString());
                }
                break;
        }
    }

    private void showCharNumber(final int maxNumber) {
        textReason.addTextChangedListener(new TextWatcher() {

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

    @Override
    public void rejectApproval() {
        if (ApprovalDetailActivity.instance != null) {
            ApprovalDetailActivity.instance.finish();
        }
        finish();
    }
}
