package com.gengcon.android.fixedassets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.adapter.ApprovalAdapter;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.ApprovalListBean;
import com.gengcon.android.fixedassets.presenter.ApprovalPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.view.ApprovalView;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ApprovalListActivity extends BaseActivity implements View.OnClickListener, ApprovalView, ApprovalAdapter.ApprovalCallback {

    private ApprovalAdapter approvalAdapter;
    private ApprovalPresenter approvalPresenter;
    private RefreshLayout mRefreshLayout;
    private List<ApprovalListBean.ListBean> approvalList;
    private MyRecyclerView recyclerView;
    private LinearLayout noDataLayout;
    private int pageCount;
    private int mPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        approvalList = new ArrayList<>();
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.approval);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_home);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        noDataLayout = findViewById(R.id.ll_no_data);
        recyclerView = findViewById(R.id.recyclerview);

        approvalPresenter = new ApprovalPresenter();
//        homePresenter = new HomePresenter();
        approvalPresenter.attachView(this);

        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (mPage <= pageCount) {
                    approvalPresenter.getApprovalList(mPage);
                } else {
                    mRefreshLayout.setEnableLoadmore(false);
                }
                mRefreshLayout.finishLoadmore();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        approvalAdapter = new ApprovalAdapter(this);
        approvalAdapter.setItemTouchListener(this);
        recyclerView.setAdapter(approvalAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        approvalPresenter.getApprovalList(1);
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
    public void showApproval(ApprovalListBean approval) {
        if (approval.getList() != null && approval.getList().size() > 0) {
            noDataLayout.setVisibility(View.GONE);
            approvalList = approval.getList();
            approvalAdapter.addDataSource(approvalList);
            pageCount = approval.getPage_count();
            if (mPage <= pageCount) {
                mPage = ++mPage;
            }
        } else {
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMoreApproval(ApprovalListBean approval) {
        if (approval.getList() != null && approval.getList().size() > 0) {
            noDataLayout.setVisibility(View.GONE);
            approvalAdapter.addMoreDataSource(approval.getList());
            pageCount = approval.getPage_count();
            if (mPage <= pageCount) {
                mPage = ++mPage;
            }
        }
    }

    @Override
    public void clickApproval(int approvalType, String approvalId) {
        Intent intent = new Intent(ApprovalListActivity.this, ApprovalDetailActivity.class);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_APPROVAL_TYPE, approvalType);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_APPROVAL_ID, approvalId);
        startActivity(intent);
    }
}
