package com.gengcon.android.fixedassets.module.user.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.EmpBean;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.bean.result.StaffManagerBean;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.module.user.presenter.StaffManagerPresenter;
import com.gengcon.android.fixedassets.module.user.view.StaffManagerView;
import com.gengcon.android.fixedassets.module.user.widget.adapter.MenuAdapter;
import com.gengcon.android.fixedassets.module.user.widget.adapter.StaffHeaderAdapter;
import com.gengcon.android.fixedassets.module.user.widget.adapter.StaffManagerOrgAdapter;
import com.gengcon.android.fixedassets.module.user.widget.adapter.StaffManagerPersonAdapter;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StaffMangerActivity extends BaseActivity implements View.OnClickListener, StaffManagerView, StaffManagerOrgAdapter.StaffManagerCallback, StaffManagerPersonAdapter.PersonDetailCallback, StaffHeaderAdapter.HeaderCallback, MenuAdapter.MenuCallback {

    private StaffManagerPresenter staffManagerPresenter;
    private StaffManagerOrgAdapter staffManagerOrgAdapter;
    private StaffManagerPersonAdapter personAdapter;
    private MyRecyclerView recyclerView;
    private RecyclerView headerRecyclerView;
    private RecyclerView menuRecyclerView;
    private MenuAdapter staffMenuAdapter;
    private LinearLayout menuLayout;
    private LinearLayout addPersonLayout;
    private View menuGoneView;
    private LinearLayout noDataLayout;
    private StaffHeaderAdapter headerAdapter;
    private List<String> headNames;
    private List<Integer> pids;
    private List<String> menuNames;
    private String companyName;

    public static int RESULT_OK = 11111;
    public static int REQUEST_CODE = 11110;

    public static int RESULT_OK_ADD_EMP = 11112;
    public static int REQUEST_CODE_ADD_EMP = 11113;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manager);
        companyName = (String) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.COMPANY_NAME, "");
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.staff_manager);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        addPersonLayout = findViewById(R.id.addPersonLayout);
        findViewById(R.id.addPersonLayout).setOnClickListener(this);
        noDataLayout = findViewById(R.id.ll_no_data);
        menuGoneView = findViewById(R.id.menuGoneView);
        menuGoneView.setOnClickListener(this);
        recyclerView = findViewById(R.id.orgRecyclerView);
        headerRecyclerView = findViewById(R.id.headerRecyclerView);
        menuLayout = findViewById(R.id.menuLayout);
        menuRecyclerView = findViewById(R.id.menuRecyclerView);
        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, false);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        staffManagerPresenter = new StaffManagerPresenter();
        staffManagerPresenter.attachView(this);
        staffManagerPresenter.getStaffManagerList(-1);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider2));
        recyclerView.addItemDecoration(divider);

        staffMenuAdapter = new MenuAdapter(this);
        staffMenuAdapter.setCallBack(this);
        menuRecyclerView.setAdapter(staffMenuAdapter);

        staffManagerOrgAdapter = new StaffManagerOrgAdapter(this, new LinearLayoutHelper());
        staffManagerOrgAdapter.setCallBack(this);
        delegateAdapter.addAdapter(staffManagerOrgAdapter);

        personAdapter = new StaffManagerPersonAdapter(this, new LinearLayoutHelper());
        personAdapter.setCallBack(this);
        delegateAdapter.addAdapter(personAdapter);
        recyclerView.setAdapter(delegateAdapter);

        headerRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        headerAdapter = new StaffHeaderAdapter(this);
        headerAdapter.setCallBack(this);
        headerRecyclerView.setAdapter(headerAdapter);
        headerRecyclerView.requestLayout();
        headNames = new ArrayList<>();
        pids = new ArrayList<>();
        menuNames = new ArrayList<>();
        headNames.add(companyName);
        pids.add(-1);
        menuNames.add("新增员工");
        headerAdapter.addDataSource(headNames, pids);
        staffMenuAdapter.addDataSource(menuNames);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.orgLayout:
                onBackPressed();
                break;
            case R.id.addPersonLayout:
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                staffManagerPresenter.getStaffManagerList(pids.get(pids.size() - 1));
            }
        } else if (resultCode == RESULT_OK_ADD_EMP) {
            if (requestCode == REQUEST_CODE_ADD_EMP) {
                staffManagerPresenter.getStaffManagerList(pids.get(pids.size() - 1));
            }
        }
    }

    @Override
    public void showStaff(StaffManagerBean staffManagerBean) {
        List<OrgBean> orgBeans = new ArrayList<>();
        List<EmpBean> empBeans = new ArrayList<>();
        if (staffManagerBean.getOrg_data() != null && staffManagerBean.getOrg_data().size() > 0) {
            orgBeans = staffManagerBean.getOrg_data();
        }
        if (staffManagerBean.getEmp_data() != null && staffManagerBean.getEmp_data().size() > 0) {
            empBeans = staffManagerBean.getEmp_data();
        }
        if (orgBeans.size() == 0 && empBeans.size() == 0) {
            noDataLayout.setVisibility(View.VISIBLE);
        } else {
            noDataLayout.setVisibility(View.GONE);
        }
        recyclerView.scrollToPosition(0);
        staffManagerOrgAdapter.addDataSource(orgBeans);
        personAdapter.addDataSource(empBeans);
        headerRecyclerView.scrollToPosition(headerAdapter.getItemCount() - 1);
    }

    @Override
    public void onFail(String msg) {

    }

    @Override
    public void clickMenu(int position) {
        menuLayout.setVisibility(View.GONE);
        menuGoneView.setVisibility(View.GONE);
        Intent intent = new Intent(this, AddEmpActivity.class);
        intent.putExtra("org_id", pids.get(pids.size() - 1));
        intent.putExtra("org_name", headNames.get(headNames.size() - 1));
        startActivityForResult(intent, REQUEST_CODE_ADD_EMP);
    }

    @Override
    public void clickItem(OrgBean orgBean) {
        addPersonLayout.setVisibility(View.VISIBLE);
        menuNames.clear();
        List<String> headerName = new ArrayList<>();
        List<Integer> pid = new ArrayList<>();
        headerName.add(orgBean.getOrg_name());
        pid.add(orgBean.getId());
        headNames.add(orgBean.getOrg_name());
        pids.add(orgBean.getId());
        headerAdapter.addDataSource(headerName, pid);
        staffManagerPresenter.getStaffManagerList(orgBean.getId());
    }

    @Override
    public void clickHeader(List<String> headerNames, List<Integer> ids, int position) {
        if (position == 0) {
            addPersonLayout.setVisibility(View.GONE);
        } else {
            addPersonLayout.setVisibility(View.VISIBLE);
        }
        pids.clear();
        headNames.clear();
        headNames.addAll(headerNames);
        pids.addAll(ids);
        headerAdapter.changeDataSource(headerNames, ids);
        staffManagerPresenter.getStaffManagerList(ids.get(position));
    }

    @Override
    public void clickPersonItem(int empId) {
        Intent intent = new Intent(this, StaffDetailActivity.class);
        intent.putExtra("emp_id", empId);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
