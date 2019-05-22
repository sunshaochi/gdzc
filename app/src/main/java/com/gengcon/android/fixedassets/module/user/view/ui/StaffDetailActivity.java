package com.gengcon.android.fixedassets.module.user.view.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.StaffDetailBean;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.module.user.presenter.StaffDetailPresenter;
import com.gengcon.android.fixedassets.module.user.view.StaffDetailView;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.widget.AlertTextDialog;

import androidx.annotation.Nullable;

public class StaffDetailActivity extends BaseActivity implements View.OnClickListener, StaffDetailView {

    private TextView orgNameView;
    private TextView staffNameView;
    private TextView jobNumberView;
    private TextView phoneView;
    private TextView currentStateView;
    private TextView positionView;
    private int empId;
    private boolean isHideMenu;
    private LinearLayout menuLayout;
    private View menuGoneView;
    private String empName;
    private boolean hasUpdate;

    private StaffDetailPresenter staffDetailPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_detail);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.staff_data);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.editEmp).setOnClickListener(this);
        findViewById(R.id.delEmp).setOnClickListener(this);
        findViewById(R.id.editEmpLayout).setOnClickListener(this);
        orgNameView = findViewById(R.id.orgNameView);
        staffNameView = findViewById(R.id.staffNameView);
        jobNumberView = findViewById(R.id.jobNumberView);
        phoneView = findViewById(R.id.phoneView);
        currentStateView = findViewById(R.id.currentStateView);
        positionView = findViewById(R.id.positionView);
        menuGoneView = findViewById(R.id.menuGoneView);
        menuLayout = findViewById(R.id.menuLayout);
        empId = getIntent().getIntExtra("emp_id", -1);
        staffDetailPresenter = new StaffDetailPresenter();
        staffDetailPresenter.attachView(this);
        staffDetailPresenter.getStaffDetail(empId);
        menuGoneView.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constant.REQUEST_CODE_EDIT_EMP && resultCode == Constant.RESULT_OK_EDIT_EMP) {
            staffDetailPresenter.getStaffDetail(empId);
            hasUpdate = true;
        }
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
            case R.id.editEmpLayout:
                isHideMenu = !isHideMenu;
                if (isHideMenu) {
                    menuLayout.setVisibility(View.VISIBLE);
                    menuGoneView.setVisibility(View.VISIBLE);
                } else {
                    menuLayout.setVisibility(View.GONE);
                    menuGoneView.setVisibility(View.GONE);
                }
                break;
            case R.id.menuGoneView:
                isHideMenu = !isHideMenu;
                menuLayout.setVisibility(View.GONE);
                menuGoneView.setVisibility(View.GONE);
                break;
            case R.id.editEmp:
                menuLayout.setVisibility(View.GONE);
                menuGoneView.setVisibility(View.GONE);
                Intent intent = new Intent(this, EditEmpActivity.class);
                intent.putExtra("emp_id", empId);
                startActivityForResult(intent, Constant.REQUEST_CODE_EDIT_EMP);
                break;
            case R.id.delEmp:
                menuLayout.setVisibility(View.GONE);
                menuGoneView.setVisibility(View.GONE);
                showDelDialog();
                break;
        }
    }

    @Override
    public void showStaffDetail(StaffDetailBean data) {
        empName = "";
        if (!TextUtils.isEmpty(data.getOrg_name())) {
            orgNameView.setText(data.getOrg_name());
        }
        if (!TextUtils.isEmpty(data.getEmp_name())) {
            staffNameView.setText(data.getEmp_name());
            empName = data.getEmp_name();
        }
        if (!TextUtils.isEmpty(data.getEmp_no())) {
            jobNumberView.setText(data.getEmp_no());
        }
        if (!TextUtils.isEmpty(data.getPhone())) {
            phoneView.setText(data.getPhone());
        }
        if (data.getStatus() == 1) {
            currentStateView.setText("在职");
        } else {
            currentStateView.setText("离职");
        }
        if (!TextUtils.isEmpty(data.getPosition())) {
            positionView.setText(data.getPosition());
        }
    }

    @Override
    public void showDelEmp() {
        ToastUtils.toastMessage(this, "员工删除成功");
        setResult(StaffMangerActivity.RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (hasUpdate) {
            setResult(StaffMangerActivity.RESULT_OK);
            finish();
        }
        super.onBackPressed();
    }

    private void showDelDialog() {
        final AlertTextDialog.Builder builder = new AlertTextDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setText("确定要删除\"" + empName + "\"吗?");
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                staffDetailPresenter.getDelEmp(empId);
                dialog.dismiss();
            }
        }, "确定");
        builder.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, "取消");
        builder.show();
    }
}
