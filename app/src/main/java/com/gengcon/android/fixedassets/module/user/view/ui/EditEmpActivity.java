package com.gengcon.android.fixedassets.module.user.view.ui;

import android.content.Intent;
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
import com.gengcon.android.fixedassets.bean.WheelBean;
import com.gengcon.android.fixedassets.bean.result.StaffDetailBean;
import com.gengcon.android.fixedassets.module.addasset.view.AddAssetDataActivity;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.module.user.presenter.EditEmpPresenter;
import com.gengcon.android.fixedassets.module.user.view.EditEmpView;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.widget.PickerLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class EditEmpActivity extends BaseActivity implements View.OnClickListener, EditEmpView, PickerLinearLayout.OnPickerListener {

    private TextView orgNameView;
    private EditText empNameEdit;
    private EditText empNoEdit;
    private EditText phoneEdit;
    private TextView empStateView;
    private EditText positionEdit;
    private Button empNameClearButton, empNoClearButton, phoneClearButton, positionClearButton;
    private int empId;

    private ImageView clearOrgNameView, arrowOrgView;

    private EditEmpPresenter editEmpPresenter;
    private JSONObject jsonObject;
    private PickerLinearLayout mLlPicker;
    private List<String> state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.edit_emp);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_right)).setText(R.string.save);
        findViewById(R.id.tv_title_right).setOnClickListener(this);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.empStateLayout).setOnClickListener(this);
        empNameClearButton = findViewById(R.id.empNameClearButton);
        empNoClearButton = findViewById(R.id.empNoClearButton);
        phoneClearButton = findViewById(R.id.phoneClearButton);
        positionClearButton = findViewById(R.id.positionClearButton);
        orgNameView = findViewById(R.id.orgNameView);
        empNameEdit = findViewById(R.id.empNameEdit);
        empNoEdit = findViewById(R.id.empNoEdit);
        phoneEdit = findViewById(R.id.phoneEdit);
        empStateView = findViewById(R.id.empStateView);
        positionEdit = findViewById(R.id.positionEdit);
        clearOrgNameView = findViewById(R.id.clearOrgNameView);
        arrowOrgView = findViewById(R.id.arrowOrgView);
        mLlPicker = findViewById(R.id.ll_picker);
        clearOrgNameView.setOnClickListener(this);
        empNameClearButton.setOnClickListener(this);
        empNoClearButton.setOnClickListener(this);
        phoneClearButton.setOnClickListener(this);
        positionClearButton.setOnClickListener(this);
        arrowOrgView.setOnClickListener(this);
        findViewById(R.id.orgNameLayout).setOnClickListener(this);
        empId = getIntent().getIntExtra("emp_id", -1);
        jsonObject = new JSONObject();
        try {
            jsonObject.put("emp_id", empId);
            jsonObject.put("emp_name", "");
            jsonObject.put("emp_no", "");
            jsonObject.put("phone", "");
            jsonObject.put("org_id", null);
            jsonObject.put("status", null);
            jsonObject.put("position", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editEmpPresenter = new EditEmpPresenter();
        editEmpPresenter.attachView(this);
        editEmpPresenter.getStaffDetail(empId);
        setAddChangeListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constant.REQUEST_BEORG && resultCode == Constant.RESULT_OK_BEORG) {
            int id = data.getIntExtra("id", -1);
            String name = data.getStringExtra("name");
            orgNameView.setText(name);
            clearOrgNameView.setVisibility(View.VISIBLE);
            arrowOrgView.setVisibility(View.GONE);
            try {
                jsonObject.put("org_id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mLlPicker.getVisibility() == View.VISIBLE) {
            mLlPicker.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }

    public void initStateData() {
        state = new ArrayList<>();
        state.add("在职");
        state.add("离职");
        List<WheelBean> stateData = new ArrayList<>();
        for (int i = 0; i < state.size(); i++) {
            WheelBean bean = new WheelBean();
            bean.setId(i + 1);
            bean.setName(state.get(i));
            stateData.add(bean);
        }
        mLlPicker.setVisibility(View.VISIBLE);
        mLlPicker.setData(stateData);
        mLlPicker.setOnSelectListener(this);
    }

    @Override
    public void showErrorMsg(int status, String msg) {
        super.showErrorMsg(status, msg);
    }

    private void setAddChangeListener() {
        empNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    empNameClearButton.setVisibility(View.VISIBLE);
                } else {
                    empNameClearButton.setVisibility(View.GONE);
                }
                try {
                    jsonObject.put("emp_name", s.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        empNoEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    empNoClearButton.setVisibility(View.VISIBLE);
                } else {
                    empNoClearButton.setVisibility(View.GONE);
                }
                try {
                    jsonObject.put("emp_no", s.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        phoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    phoneClearButton.setVisibility(View.VISIBLE);
                } else {
                    phoneClearButton.setVisibility(View.GONE);
                }
                try {
                    jsonObject.put("phone", s.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        positionEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    positionClearButton.setVisibility(View.VISIBLE);
                } else {
                    positionClearButton.setVisibility(View.GONE);
                }
                try {
                    jsonObject.put("position", s.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
            case R.id.tv_title_right:
                editEmpPresenter.getEditEmp(jsonObject.toString());
                break;
            case R.id.orgNameLayout:
                Intent intent = new Intent(this, AddAssetDataActivity.class);
                intent.putExtra("addAssetType", 1);
                startActivityForResult(intent, Constant.REQUEST_BEORG);
                break;
            case R.id.empStateLayout:
                hideSoftInput();
                initStateData();
                break;
            case R.id.clearOrgNameView:
                orgNameView.setText("");
                try {
                    jsonObject.put("org_id", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                clearOrgNameView.setVisibility(View.GONE);
                arrowOrgView.setVisibility(View.VISIBLE);
                break;
            case R.id.empNameClearButton:
                empNameEdit.setText("");
                break;
            case R.id.empNoClearButton:
                empNoEdit.setText("");
                break;
            case R.id.phoneClearButton:
                phoneEdit.setText("");
                break;
            case R.id.positionClearButton:
                positionEdit.setText("");
                break;
        }
    }

    @Override
    public void onConfirm(WheelBean bean) {
        empStateView.setText(bean.getName());
        try {
            jsonObject.put("status", bean.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void editEmpSuccess() {
        ToastUtils.toastMessage(this, "员工修改成功");
        setResult(Constant.RESULT_OK_EDIT_EMP);
        finish();
    }

    @Override
    public void editEmpFail(String msg) {
        ToastUtils.toastMessage(this, msg);
    }

    @Override
    public void showEmpDetail(StaffDetailBean data) {
        if (!TextUtils.isEmpty(data.getOrg_name())) {
            orgNameView.setText(data.getOrg_name());
            clearOrgNameView.setVisibility(View.VISIBLE);
            arrowOrgView.setVisibility(View.GONE);
            try {
                jsonObject.put("org_id", data.getOrg_id());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(data.getEmp_name())) {
            empNameEdit.setText(data.getEmp_name());
        }
        if (!TextUtils.isEmpty(data.getEmp_no())) {
            empNoEdit.setText(data.getEmp_no());
        }
        if (!TextUtils.isEmpty(data.getPhone())) {
            phoneEdit.setText(data.getPhone());
        }
        if (data.getStatus() == 1) {
            empStateView.setText("在职");
        } else {
            empStateView.setText("离职");
        }
        try {
            jsonObject.put("status", data.getStatus());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(data.getPosition())) {
            positionEdit.setText(data.getPosition());
        }
    }
}
