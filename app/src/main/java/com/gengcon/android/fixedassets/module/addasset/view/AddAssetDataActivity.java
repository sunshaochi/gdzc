package com.gengcon.android.fixedassets.module.addasset.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.ClassificationBean;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.module.addasset.presenter.AddAssetDataPresenter;
import com.gengcon.android.fixedassets.module.addasset.widget.adapter.AddAssetDataAdapter;
import com.gengcon.android.fixedassets.module.addasset.widget.adapter.AddAssetDataHeaderAdapter;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class AddAssetDataActivity extends BaseActivity implements View.OnClickListener, AddAssetDataView, AddAssetDataAdapter.AddAssetDataCallback, AddAssetDataHeaderAdapter.HeaderCallback {

    private AddAssetDataAdapter addAssetDataAdapter;
    private AddAssetDataPresenter addAssetDataPresenter;
    private AddAssetDataHeaderAdapter headerAdapter;
    private MyRecyclerView recyclerView;
    private RecyclerView headerRecyclerView;
    private List<String> headNames;
    private List<List<OrgBean>> orgDatas;
    private List<List<ClassificationBean>> classificationDatas;
    private int addAssetType;
    private String companyName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset_org);
        addAssetType = getIntent().getIntExtra("addAssetType", -1);
        companyName = (String) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.COMPANY_NAME, "");
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        if (addAssetType == 1) {
            ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.add_asset_org);
        } else if (addAssetType == 2) {
            ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.add_asset_classification);
        } else if (addAssetType == 3) {
            ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.add_asset_use_org);
        }
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerview);
        headerRecyclerView = findViewById(R.id.headerRecyclerView);
        addAssetDataPresenter = new AddAssetDataPresenter();
//        homePresenter = new HomePresenter();
        addAssetDataPresenter.attachView(this);
        if (addAssetType == 1 || addAssetType == 3) {
            addAssetDataPresenter.getOrgList();
        } else if (addAssetType == 2) {
            addAssetDataPresenter.getClassificationList();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider2));
        recyclerView.addItemDecoration(divider);
        addAssetDataAdapter = new AddAssetDataAdapter(this, addAssetType);
        addAssetDataAdapter.setCallBack(this);
        recyclerView.setAdapter(addAssetDataAdapter);

        headerRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        headerAdapter = new AddAssetDataHeaderAdapter(this);
        headerAdapter.setCallBack(this);
        headerRecyclerView.setAdapter(headerAdapter);
        headerRecyclerView.requestLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        }
    }

    @Override
    public void showOrg(List<OrgBean> orgBeans) {
        orgDatas = new ArrayList<>();
        headNames = new ArrayList<>();
        orgDatas.add(orgBeans);
        headNames.clear();
        headNames.add(companyName);
        addAssetDataAdapter.addOrgDataSource(orgBeans);
        headerAdapter.addDataSource(headNames);
    }

    @Override
    public void showClassification(List<ClassificationBean> classificationBeans) {
        classificationDatas = new ArrayList<>();
        headNames = new ArrayList<>();
        classificationDatas.add(classificationBeans);
        headNames.clear();
        headNames.add("资产分类");
        addAssetDataAdapter.addClassificationDataSource(classificationBeans);
        headerAdapter.addDataSource(headNames);
    }

    @Override
    public void clickDataItem(int dataId, String name) {
        Intent intent = new Intent();
        intent.putExtra("id", dataId);
        intent.putExtra("name", name);
        if (addAssetType == 1) {
            setResult(Constant.RESULT_OK_BEORG, intent);
            finish();
        } else if (addAssetType == 2) {
            setResult(AddAssetActivity.RESULT_OK_CLASSIFICATION, intent);
            finish();
        } else if (addAssetType == 3) {
            setResult(AddAssetActivity.RESULT_OK_USEORG, intent);
            finish();
        }
    }

    @Override
    public void reloadOrg(List<OrgBean> orgBeans, String orgName) {
        List<String> headerNames = new ArrayList<>();
        headerNames.add(orgName);
        orgDatas.add(orgBeans);
        addAssetDataAdapter.addOrgDataSource(orgBeans);
        headerAdapter.addDataSource(headerNames);
        headerRecyclerView.scrollToPosition(headerAdapter.getItemCount() - 1);
    }

    @Override
    public void reloadClassification(List<ClassificationBean> classificationBeans, String classificationName) {
        List<String> headerNames = new ArrayList<>();
        headerNames.add(classificationName);
        classificationDatas.add(classificationBeans);
        addAssetDataAdapter.addClassificationDataSource(classificationBeans);
        headerAdapter.addDataSource(headerNames);
        headerRecyclerView.scrollToPosition(headerAdapter.getItemCount() - 1);
    }

    @Override
    public void clickHeader(List<String> headerNames, int position) {
        headerAdapter.changeDataSource(headerNames);
        if (addAssetType == 1 || addAssetType == 3) {
            addAssetDataAdapter.addOrgDataSource(orgDatas.get(position));
            List<List<OrgBean>> datas = new ArrayList<>();
            for (int i = 0; i < position + 1; i++) {
                datas.add(orgDatas.get(i));
            }
            orgDatas.clear();
            orgDatas.addAll(datas);
        } else if (addAssetType == 2) {
            addAssetDataAdapter.addClassificationDataSource(classificationDatas.get(position));
            List<List<ClassificationBean>> datas = new ArrayList<>();
            for (int i = 0; i < position + 1; i++) {
                datas.add(classificationDatas.get(i));
            }
            classificationDatas.clear();
            classificationDatas.addAll(datas);
        }
    }
}
