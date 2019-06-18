package com.gengcon.android.fixedassets.module.user.view.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.module.user.presenter.OrgSettingSecondPresenter;
import com.gengcon.android.fixedassets.module.user.view.OrgSettingSecondView;
import com.gengcon.android.fixedassets.module.user.widget.adapter.OrgHeaderAdapter;
import com.gengcon.android.fixedassets.module.user.widget.adapter.MenuAdapter;
import com.gengcon.android.fixedassets.module.user.widget.adapter.OrgSettingSecondAdapter;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.widget.AlertEditDialog;
import com.gengcon.android.fixedassets.widget.AlertTextDialog;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrgSettingSecondActivity extends BaseActivity implements View.OnClickListener, OrgSettingSecondView, OrgSettingSecondAdapter.OrgSettingSecondCallback, OrgHeaderAdapter.HeaderCallback, MenuAdapter.MenuCallback {

    private OrgSettingSecondAdapter orgSettingSecondAdapter;
    private OrgSettingSecondPresenter orgSettingSecondPresenter;
    private OrgHeaderAdapter headerAdapter;
    private MyRecyclerView recyclerView;
    private RecyclerView headerRecyclerView;
    private RecyclerView menuRecyclerView;
    private LinearLayout menuLayout;
    private List<String> headNamelist;
    private List<Integer> pidlist;
    private List<String> menuNamelist;
    private MenuAdapter orgMenuAdapter;
    private View menuGoneView;
    private String dialogTitle;
    private JSONObject addOrgJson;
    private JSONObject editOrgJson;
    private JSONObject delOrgJson;
    private int orgId;
    private String org_code;
    private String defaultcode;
    private boolean isFatherOrg;
    private List<OrgBean> orgDatas;
    private LinearLayout noDataLayout;
    private String companyName;
    private DialogInterface addDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_setting_second);
        isFatherOrg = true;
        companyName = (String) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.COMPANY_NAME, "");
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.org_setting_second);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.addOrgLayout).setOnClickListener(this);
        noDataLayout = findViewById(R.id.ll_no_data);
        menuGoneView = findViewById(R.id.menuGoneView);
        menuGoneView.setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerview);
        headerRecyclerView = findViewById(R.id.headerRecyclerView);
        menuLayout = findViewById(R.id.menuLayout);
        menuRecyclerView = findViewById(R.id.menuRecyclerView);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        orgSettingSecondPresenter = new OrgSettingSecondPresenter();
        orgSettingSecondPresenter.attachView(this);
        orgSettingSecondPresenter.getOrgSettingList(-1);//获取组织信息

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider2));
        recyclerView.addItemDecoration(divider);

        orgMenuAdapter = new MenuAdapter(this);
        orgMenuAdapter.setCallBack(this);
        menuRecyclerView.setAdapter(orgMenuAdapter);

        orgSettingSecondAdapter = new OrgSettingSecondAdapter(this);
        orgSettingSecondAdapter.setCallBack(this);
        recyclerView.setAdapter(orgSettingSecondAdapter);

        headerRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        headerAdapter = new OrgHeaderAdapter(this);
        headerAdapter.setCallBack(this);
        headerRecyclerView.setAdapter(headerAdapter);
        headerRecyclerView.requestLayout();
        orgDatas = new ArrayList<>();
        headNamelist = new ArrayList<>();
        pidlist = new ArrayList<>();
        menuNamelist = new ArrayList<>();
        headNamelist.add(companyName);
        menuNamelist.add("新增子公司");
        menuNamelist.add("新增子部门");
        pidlist.add(-1);
        headerAdapter.addDataSource(headNamelist, pidlist);
        orgMenuAdapter.addDataSource(menuNamelist);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.addOrgLayout:
                menuLayout.setVisibility(View.VISIBLE);
                menuGoneView.setVisibility(View.VISIBLE);
                break;
            case R.id.menuGoneView:
                menuLayout.setVisibility(View.GONE);
                menuGoneView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void showOrg(List<OrgBean> orgBeans) {
        if (orgBeans != null && orgBeans.size() > 0) {
            noDataLayout.setVisibility(View.GONE);
            if (isFatherOrg) {
                orgId = orgBeans.get(0).getPid();//只返回一个
                if (!TextUtils.isEmpty(orgBeans.get(0).getOrg_code()))
//                    org_code = orgBeans.get(0).getOrg_code();
                    org_code = TextUtils.isEmpty(orgBeans.get(0).getOrg_code())?"":orgBeans.get(0).getOrg_code();
            }
        } else {
            noDataLayout.setVisibility(View.VISIBLE);
        }
        isFatherOrg = false;
        orgMenuAdapter.addDataSource(menuNamelist);
        orgSettingSecondAdapter.addDataSource(orgBeans);
        headerRecyclerView.scrollToPosition(headerAdapter.getItemCount() - 1);
    }


    @Override
    public void clickHeader(List<String> headerNames, List<Integer> pid, int position) {
        menuNamelist.clear();
        if (position == 0) {
            menuNamelist.add("新增子公司");
            menuNamelist.add("新增子部门");
            orgDatas.clear();
            isFatherOrg = true;
        } else {
            OrgBean orgBean = orgDatas.get(position - 1);
            orgId = orgBean.getId();
//            if (!TextUtils.isEmpty(orgBean.getOrg_code())) {
//                org_code = orgBean.getOrg_code();
//            }
            org_code = TextUtils.isEmpty(orgBean.getOrg_code())?"":orgBean.getOrg_code();
            if (orgBean.getType() == 1) {
                menuNamelist.add("新增子公司");
                menuNamelist.add("新增子部门");
            } else if (orgBean.getType() == 2) {
                menuNamelist.add("新增子部门");
            }
            menuNamelist.add("编辑");
            List<OrgBean> orgBeans = new ArrayList<>();
            for (int i = 0; i < position; i++) {
                orgBeans.add(orgDatas.get(i));
            }
            orgDatas.clear();
            orgDatas.addAll(orgBeans);
            isFatherOrg = false;
        }
        pidlist.clear();
        headNamelist.clear();
        headNamelist.addAll(headerNames);
        pidlist.addAll(pid);
        headerAdapter.changeDataSource(headerNames, pid);
        orgSettingSecondPresenter.getOrgSettingList(pid.get(position));
    }

    @Override
    public void clickItem(OrgBean orgBean) {
        menuNamelist.clear();
        isFatherOrg = false;
        List<String> headerName = new ArrayList<>();
        List<Integer> pid = new ArrayList<>();
        headerName.add(orgBean.getOrg_name());
        pid.add(orgBean.getId());
        orgId = orgBean.getId();
//        if (!TextUtils.isEmpty(orgBean.getOrg_code())) {
//            org_code = orgBean.getOrg_code();
//        }
        org_code = TextUtils.isEmpty(orgBean.getOrg_code())?"":orgBean.getOrg_code();
        if (orgBean.getType() == 1) {
            menuNamelist.add("新增子公司");
            menuNamelist.add("新增子部门");
        } else if (orgBean.getType() == 2) {
            menuNamelist.add("新增子部门");
        }
        menuNamelist.add("编辑");
        if (orgBean.getChildren() == null || orgBean.getChildren().size() == 0) {
            menuNamelist.add("删除");
        }
        orgDatas.add(orgBean);
        headNamelist.add(orgBean.getOrg_name());
        pidlist.add(orgBean.getId());
        headerAdapter.addDataSource(headerName, pid);//跟新头部
        orgSettingSecondPresenter.getOrgSettingList(orgBean.getId());
    }

    @Override
    public void clickMenu(int position) {
        menuLayout.setVisibility(View.GONE);
        menuGoneView.setVisibility(View.GONE);
        dialogTitle = menuNamelist.get(position);
        if (dialogTitle.equals("删除")) {
            showDelDialog();
        } else {
            if (dialogTitle.equals("编辑")) {
                showEditDialog();
            } else {
                orgSettingSecondPresenter.getDefaultCode(1);
            }
        }
    }

    private void showEditDialog() {
        final AlertEditDialog.Builder builder = new AlertEditDialog.Builder(this);
        if (dialogTitle.equals("编辑")) {
            builder.setTitle(dialogTitle + "组织信息");
            builder.setEditText(headNamelist.get(headNamelist.size() - 1));
            if (!TextUtils.isEmpty(org_code)) {
                builder.setEditCode(org_code);
            }
//            builder.setEnable(false);
              builder.setEnable(true);
        } else {
            builder.setTitle(dialogTitle + "");
            if (!TextUtils.isEmpty(defaultcode)) {
                builder.setEditCode(defaultcode);
            }
            builder.setEnable(true);
        }

        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addDialog = dialog;
//                if (TextUtils.isEmpty(builder.getEditCode())) {
//                    ToastUtils.toastMessage(OrgSettingSecondActivity.this, "请输入组织编码");
//                    return;
//                }
//                if (builder.getEditCode().length() < 8) {
//                    ToastUtils.toastMessage(OrgSettingSecondActivity.this, "组织编码格式错误（8位，可包含大写英文字母，数字 ）");
//                    return;
//                }
                if (TextUtils.isEmpty(builder.getEditText())) {
                    ToastUtils.toastMessage(OrgSettingSecondActivity.this, "请输入组织名称");
                    return;
                }

                if (builder.getEditText().length() < 2 || builder.getEditText().length() > 30) {
                    ToastUtils.toastMessage(OrgSettingSecondActivity.this, "组织名称格式错误（2-30位，可包含中文，数字，字母，符号“.-_()”）");
                    return;
                }

                switch (dialogTitle) {
                    case "新增子公司":
                        addOrgJson = new JSONObject();
                        try {
                            addOrgJson.put("org_name", builder.getEditText());
                            addOrgJson.put("pid", orgId);
                            addOrgJson.put("type", 1);
                            addOrgJson.put("org_code", builder.getEditCode());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        orgSettingSecondPresenter.addOrg(addOrgJson.toString());
                        break;
                    case "新增子部门":
                        addOrgJson = new JSONObject();
                        try {
                            addOrgJson.put("org_name", builder.getEditText());
                            addOrgJson.put("pid", orgId);
                            addOrgJson.put("type", 2);
                            addOrgJson.put("org_code", builder.getEditCode());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        orgSettingSecondPresenter.addOrg(addOrgJson.toString());
                        break;
                    case "编辑":
//                        if (headNamelist.get(headNamelist.size() - 1).equals(builder.getEditText())&&org_code.equals(builder.getEditCode())) {
//                            dialog.dismiss();
//                            Log.e("OrgSecond", "headNamelist: " + headNamelist.get(headNamelist.size() - 1) + "builder.getEditText(): " + builder.getEditText());
//                        } else {
                            editOrgJson = new JSONObject();
                            try {
                                editOrgJson.put("org_name", builder.getEditText());
                                editOrgJson.put("org_id", orgId);
                                editOrgJson.put("org_code", builder.getEditCode());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            orgSettingSecondPresenter.editOrg(editOrgJson.toString());
//                        }
                        break;
                }


            }
        }, "保存");
        builder.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, "取消");
        builder.show();
    }

    private void showDelDialog() {
        final AlertTextDialog.Builder builder = new AlertTextDialog.Builder(this);
        builder.setTitle(dialogTitle);
        builder.setText("确定要删除\"" + headNamelist.get(headNamelist.size() - 1) + "\"吗?");
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delOrgJson = new JSONObject();
                try {
                    delOrgJson.put("org_id", orgId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                orgSettingSecondPresenter.delOrg(delOrgJson.toString());
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

    @Override
    public void addOrg(String msg) {
        addDialog.dismiss();
        ToastUtils.toastMessage(this, msg);
        if (pidlist.size() == 1) {
            orgSettingSecondPresenter.getOrgSettingList(-1);
        } else {
            if (menuNamelist.contains("删除")) {
                menuNamelist.remove("删除");
            }
            orgSettingSecondPresenter.getOrgSettingList(orgId);
        }
    }

    @Override
    public void onFail(String msg) {
        ToastUtils.toastMessage(this, msg);
    }

    @Override
    public void editOrg(String msg) {
        ToastUtils.toastMessage(this, msg);
        addDialog.dismiss();
        menuNamelist.clear();
        headNamelist.remove(headNamelist.get(headNamelist.size() - 1));
        pidlist.remove(pidlist.get(pidlist.size() - 1));
        if (pidlist.size() == 1) {
            menuNamelist.add("新增子公司");
            menuNamelist.add("新增子部门");
            orgSettingSecondPresenter.getOrgSettingList(-1);
        } else {
            orgDatas.remove(orgDatas.get(orgDatas.size() - 1));
            OrgBean orgBean = orgDatas.get(orgDatas.size() - 1);
            if (orgBean.getType() == 1) {
                menuNamelist.add("新增子公司");
                menuNamelist.add("新增子部门");
            } else if (orgBean.getType() == 2) {
                menuNamelist.add("新增子部门");
            }
            menuNamelist.add("编辑");
            if (orgBean.getChildren() == null || orgBean.getChildren().size() == 0) {
                menuNamelist.add("删除");
            }
            orgId = pidlist.get(pidlist.size() - 1);
//            if (!TextUtils.isEmpty(orgBean.getOrg_code())) {
//                org_code = orgBean.getOrg_code();
//            }
            org_code = TextUtils.isEmpty(orgBean.getOrg_code())?"":orgBean.getOrg_code();
            orgSettingSecondPresenter.getOrgSettingList(pidlist.get(pidlist.size() - 1));
        }
        headerAdapter.changeDataSource(headNamelist, pidlist);
    }


    @Override
    public void delOrg(String msg) {
        ToastUtils.toastMessage(this, msg);
        menuNamelist.clear();
        headNamelist.remove(headNamelist.get(headNamelist.size() - 1));
        pidlist.remove(pidlist.get(pidlist.size() - 1));
        if (pidlist.size() == 1) {
            orgSettingSecondPresenter.getOrgSettingList(-1);
        } else {
            orgDatas.remove(orgDatas.get(orgDatas.size() - 1));
            OrgBean orgBean = orgDatas.get(orgDatas.size() - 1);
            if (orgBean.getType() == 1) {
                menuNamelist.add("新增子公司");
                menuNamelist.add("新增子部门");
            } else if (orgBean.getType() == 2) {
                menuNamelist.add("新增子部门");
            }
            menuNamelist.add("编辑");
            if (orgBean.getChildren() == null || orgBean.getChildren().size() == 0 || orgBean.getChildren().size() == 1) {
                menuNamelist.add("删除");
            }
            orgId = pidlist.get(pidlist.size() - 1);
//            if (!TextUtils.isEmpty(orgBean.getOrg_code())) {
//                org_code = orgBean.getOrg_code();
//            }
            org_code = TextUtils.isEmpty(orgBean.getOrg_code())?"":orgBean.getOrg_code();
            orgSettingSecondPresenter.getOrgSettingList(pidlist.get(pidlist.size() - 1));
        }
        headerAdapter.changeDataSource(headNamelist, pidlist);
    }

    @Override
    public void getCodeSuc(String code) {
        defaultcode = code;
        showEditDialog();
    }


}
