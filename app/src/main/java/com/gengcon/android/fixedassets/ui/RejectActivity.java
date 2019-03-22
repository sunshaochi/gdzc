package com.gengcon.android.fixedassets.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
import com.gengcon.android.fixedassets.presenter.ApprovalRejectPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.ApprovalRejectView;
import com.gengcon.android.fixedassets.widget.AlertDialog;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class RejectActivity extends BaseActivity implements View.OnClickListener, ApprovalRejectView {

    private EditText textReason;
    private TextView textCount;
    private Button sureButton;
    private String doc_no;
    private ApprovalRejectPresenter presenter;
    private static int MAX_COUNT = 40;

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
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.reject);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        textCount = findViewById(R.id.textCount);
        textReason = findViewById(R.id.textReason);
        sureButton = findViewById(R.id.sureButton);
        sureButton.setOnClickListener(this);
        textCount.setText(0 + "/" + MAX_COUNT);
        showCharNumber(MAX_COUNT);
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
            case R.id.iv_title_left:
                onBackPressed();
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
        setResult(ApprovalDetailActivity.RESULT_OK);
        finish();
    }

    @Override
    public void contractExpire() {
        showContractExpireDiolog();
    }

    private void showContractExpireDiolog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, false);
        builder.setTitle("温馨提示");
        String content = "  您的账号使用期限已到期" + "\n" + "如需继续使用，请联系客服";
        builder.setText(content);
        builder.setUpDate(false);
        builder.setNeutralButtonColor();
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onCall("4008608800");
            }
        }, "立即联系");
        builder.setNeutralButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                    mPresenter.getReadEditNotice(userPopupNotice.getList().getId());
                dialog.dismiss();
            }
        }, "稍后联系");

        builder.show();
    }

    public void callPhoneNumber(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;

    //动态权限申请后处理
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted callDirectly(mobile);
                } else {
                    // Permission Denied Toast.makeText(MainActivity.this,"CALL_PHONE Denied", Toast.LENGTH_SHORT) .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void onCall(String mobile) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CALL_PHONE
                }, REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                callPhoneNumber(mobile);
            }
        } else {
            callPhoneNumber(mobile);
        }
    }
}
