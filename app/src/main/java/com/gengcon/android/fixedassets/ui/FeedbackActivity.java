package com.gengcon.android.fixedassets.ui;

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
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.presenter.FeedbackPresenter;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.FeedbackView;

import androidx.annotation.Nullable;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener, FeedbackView {

    private EditText feedbackEdit;
    private TextView textCount;
    private Button sureButton;
    private FeedbackPresenter presenter;
    private static int MAX_COUNT = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        presenter = new FeedbackPresenter();
        presenter.attachView(this);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.feedback);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        textCount = findViewById(R.id.textCount);
        feedbackEdit = findViewById(R.id.feedbackEdit);
        sureButton = findViewById(R.id.sureButton);
        sureButton.setOnClickListener(this);
        textCount.setText(0 + "/" + MAX_COUNT);
        showCharNumber(MAX_COUNT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sureButton:
                if (TextUtils.isEmpty(feedbackEdit.getText().toString())) {
                    ToastUtils.toastMessage(this, "请填写意见后再提交");
                } else {
                    presenter.getFeedback(feedbackEdit.getText().toString());
                }
                break;
            case R.id.iv_title_left:
                onBackPressed();
                break;
        }
    }

    private void showCharNumber(final int maxNumber) {
        feedbackEdit.addTextChangedListener(new TextWatcher() {

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
    public void feedback() {
        if (ApprovalDetailActivity.instance != null) {
            ApprovalDetailActivity.instance.finish();
        }
        ToastUtils.toastMessage(this, "提交成功");
        finish();
    }
}
