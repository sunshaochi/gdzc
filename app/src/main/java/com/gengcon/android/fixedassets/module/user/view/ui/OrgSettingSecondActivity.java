package com.gengcon.android.fixedassets.module.user.view.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.module.addasset.view.AddAssetActivity;
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
    private List<String> headNames;
    private List<String> menuNames;
    private List<Integer> pids;
    private MenuAdapter orgMenuAdapter;
    private View menuGoneView;
    private String dialogTitle;
    private JSONObject addOrgJson;
    private JSONObject editOrgJson;
    private JSONObject delOrgJson;
    private int orgId;
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
        orgSettingSecondPresenter.getOrgSettingList(-1);

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
        headNames = new ArrayList<>();
        pids = new ArrayList<>();
        menuNames = new ArrayList<>();
        headNames.add(companyName);
        menuNames.add("新增子公司");
        menuNames.add("新增子部门");
        pids.add(-1);
        headerAdapter.addDataSource(headNames, pids);
        orgMenuAdapter.addDataSource(menuNames);
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
                orgId = orgBeans.get(0).getPid();
            }
        } else {
            noDataLayout.setVisibility(View.VISIBLE);
        }
        isFatherOrg = false;
        orgMenuAdapter.addDataSource(menuNames);
        orgSettingSecondAdapter.addDataSource(orgBeans);
        headerRecyclerView.scrollToPosition(headerAdapter.getItemCount() - 1);
    }

    @Override
    public void addOrg(String msg) {
        addDialog.dismiss();
        ToastUtils.toastMessage(this, msg);
        if (pids.size() == 1) {
            orgSettingSecondPresenter.getOrgSettingList(-1);
        } else {
            if (menuNames.contains("删除")) {
                menuNames.remove("删除");
            }
            orgSettingSecondPresenter.getOrgSettingList(orgId);
        }
    }

    @Override
    public void onFail(String msg) {
        ToastUtils.toastMessage(this, msg);
    }

    @Override
    public void editOrg() {
        menuNames.clear();
        headNames.remove(headNames.get(headNames.size() - 1));
        pids.remove(pids.get(pids.size() - 1));
        if (pids.size() == 1) {
            menuNames.add("新增子公司");
            menuNames.add("新增子部门");
            orgSettingSecondPresenter.getOrgSettingList(-1);
        } else {
            orgDatas.remove(orgDatas.get(orgDatas.size() - 1));
            OrgBean orgBean = orgDatas.get(orgDatas.size() - 1);
            if (orgBean.getType() == 1) {
                menuNames.add("新增子公司");
                menuNames.add("新增子部门");
            } else if (orgBean.getType() == 2) {
                menuNames.add("新增子部门");
            }
            menuNames.add("编辑");
            if (orgBean.getChildren() == null || orgBean.getChildren().size() == 0) {
                menuNames.add("删除");
            }
            orgId = pids.get(pids.size() - 1);
            orgSettingSecondPresenter.getOrgSettingList(pids.get(pids.size() - 1));
        }
        headerAdapter.changeDataSource(headNames, pids);
    }

    @Override
    public void delOrg() {
        menuNames.clear();
        headNames.remove(headNames.get(headNames.size() - 1));
        pids.remove(pids.get(pids.size() - 1));
        if (pids.size() == 1) {
            orgSettingSecondPresenter.getOrgSettingList(-1);
        } else {
            orgDatas.remove(orgDatas.get(orgDatas.size() - 1));
            OrgBean orgBean = orgDatas.get(orgDatas.size() - 1);
            if (orgBean.getType() == 1) {
                menuNames.add("新增子公司");
                menuNames.add("新增子部门");
            } else if (orgBean.getType() == 2) {
                menuNames.add("新增子部门");
            }
            menuNames.add("编辑");
            if (orgBean.getChildren() == null || orgBean.getChildren().size() == 0 || orgBean.getChildren().size() == 1) {
                menuNames.add("删除");
            }
            orgId = pids.get(pids.size() - 1);
            orgSettingSecondPresenter.getOrgSettingList(pids.get(pids.size() - 1));
        }
        headerAdapter.changeDataSource(headNames, pids);
    }

    @Override
    public void clickHeader(List<String> headerNames, List<Integer> pid, int position) {
        menuNames.clear();
        if (position == 0) {
            menuNames.add("新增子公司");
            menuNames.add("新增子部门");
            orgDatas.clear();
            isFatherOrg = true;
        } else {
            OrgBean orgBean = orgDatas.get(position - 1);
            orgId = orgBean.getId();
            if (orgBean.getType() == 1) {
                menuNames.add("新增子公司");
                menuNames.add("新增子部门");
            } else if (orgBean.getType() == 2) {
                menuNames.add("新增子部门");
            }
            menuNames.add("编辑");
            List<OrgBean> orgBeans = new ArrayList<>();
            for (int i = 0; i < position; i++) {
                orgBeans.add(orgDatas.get(i));
            }
            orgDatas.clear();
            orgDatas.addAll(orgBeans);
            isFatherOrg = false;
        }
        pids.clear();
        headNames.clear();
        headNames.addAll(headerNames);
        pids.addAll(pid);
        headerAdapter.changeDataSource(headerNames, pid);
        orgSettingSecondPresenter.getOrgSettingList(pid.get(position));
    }

    @Override
    public void clickItem(OrgBean orgBean) {
        menuNames.clear();
        isFatherOrg = false;
        List<String> headerName = new ArrayList<>();
        List<Integer> pid = new ArrayList<>();
        headerName.add(orgBean.getOrg_name());
        pid.add(orgBean.getId());
        orgId = orgBean.getId();
        if (orgBean.getType() == 1) {
            menuNames.add("新增子公司");
            menuNames.add("新增子部门");
        } else if (orgBean.getType() == 2) {
            menuNames.add("新增子部门");
        }
        menuNames.add("编辑");
        if (orgBean.getChildren() == null || orgBean.getChildren().size() == 0) {
            menuNames.add("删除");
        }
        orgDatas.add(orgBean);
        headNames.add(orgBean.getOrg_name());
        pids.add(orgBean.getId());
        headerAdapter.addDataSource(headerName, pid);
        orgSettingSecondPresenter.getOrgSettingList(orgBean.getId());
    }

    @Override
    public void clickMenu(int position) {
        menuLayout.setVisibility(View.GONE);
        menuGoneView.setVisibility(View.GONE);
        dialogTitle = menuNames.get(position);
        if (dialogTitle.equals("删除")) {
            showDelDialog();
        } else {
            showEditDialog();
        }
    }

    private void showEditDialog() {
        final AlertEditDialog.Builder builder = new AlertEditDialog.Builder(this);
        builder.setTitle(dialogTitle);
        if (dialogTitle.equals("编辑")) {
            builder.setEditText(headNames.get(headNames.size() - 1));
        }
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addDialog = dialog;
                if (TextUtils.isEmpty(builder.getEditText())) {
                    ToastUtils.toastMessage(OrgSettingSecondActivity.this, "请输入组织名称");
                } else {
                    if (builder.getEditText().length() < 2 || builder.getEditText().length() > 30) {
                        ToastUtils.toastMessage(OrgSettingSecondActivity.this, "组织名称格式错误（2-30位，可包含中文，数字，字母，符号“.-_()”）");
                    } else {
                        switch (dialogTitle) {
                            case "新增子公司":
                                addOrgJson = new JSONObject();
                                try {
                                    addOrgJson.put("org_name", builder.getEditText());
                                    addOrgJson.put("pid", orgId);
                                    addOrgJson.put("type", 1);
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
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                orgSettingSecondPresenter.addOrg(addOrgJson.toString());
                                break;
                            case "编辑":
                                editOrgJson = new JSONObject();
                                try {
                                    editOrgJson.put("org_name", builder.getEditText());
                                    editOrgJson.put("org_id", orgId);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                orgSettingSecondPresenter.editOrg(editOrgJson.toString());
                                break;
                        }
                    }
                }
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

    private void showDelDialog() {
        final AlertTextDialog.Builder builder = new AlertTextDialog.Builder(this);
        builder.setTitle(dialogTitle);
        builder.setText("确定要删除\"" + headNames.get(headNames.size() - 1) + "\"吗?");
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
}
