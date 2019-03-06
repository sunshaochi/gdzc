package com.gengcon.android.fixedassets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.adapter.ApprovalAssetAdapter;
import com.gengcon.android.fixedassets.adapter.OnItemClickListener;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.AssetBean;
import com.gengcon.android.fixedassets.bean.result.ApprovalDetailBean;
import com.gengcon.android.fixedassets.bean.result.ApprovalHeadDetail;
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.presenter.ApprovalDetailPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.view.ApprovalDetailView;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ApprovalDetailActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener, ApprovalDetailView {

    private TextView text1_0, text1_1,
            text2_0, text2_1,
            text3_0, text3_1,
            text4_0, text4_1,
            text5_0, text5_1,
            text6_0, text6_1, assetSizeView;
    private LinearLayout layout1, layout2, layout3, layout4, layout5, layout6, rejectButton, agreeButton;
    private RefreshLayout mRefreshLayout;
    private MyRecyclerView mRecyclerView;
    private ApprovalAssetAdapter mAdapter;
    private ArrayList<AssetBean> mAssets;
    private int mPage = 1;

    private int totalPage;

    private int assetSize;

    private String doc_no;

    private int doc_type;

    private ApprovalDetailPresenter presenter;

    public static ApprovalDetailActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_approval);
        instance = this;
        mAssets = new ArrayList<>();
        initIntent(getIntent());
        initView();
    }

    private void initIntent(Intent intent) {
        doc_no = intent.getStringExtra(Constant.INTENT_EXTRA_KEY_APPROVAL_ID);
        doc_type = intent.getIntExtra(Constant.INTENT_EXTRA_KEY_APPROVAL_TYPE, -1);
    }

    @Override
    protected void initView() {
        if (doc_type == 3) {
            ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.allocation_detail);
        } else if (doc_type == 6) {
            ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.scrap_detail);
        }
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        text1_0 = findViewById(R.id.text1_0);
        text1_1 = findViewById(R.id.text1_1);
        text2_0 = findViewById(R.id.text2_0);
        text2_1 = findViewById(R.id.text2_1);
        text3_0 = findViewById(R.id.text3_0);
        text3_1 = findViewById(R.id.text3_1);
        text4_0 = findViewById(R.id.text4_0);
        text4_1 = findViewById(R.id.text4_1);
        text5_0 = findViewById(R.id.text5_0);
        text5_1 = findViewById(R.id.text5_1);
        text6_0 = findViewById(R.id.text6_0);
        text6_1 = findViewById(R.id.text6_1);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        layout5 = findViewById(R.id.layout5);
        layout6 = findViewById(R.id.layout6);
        assetSizeView = findViewById(R.id.assetSizeView);

        presenter = new ApprovalDetailPresenter();
        presenter.attachView(this);
        presenter.getApprovalDetail(doc_no, mPage);
        presenter.getDetailHead(doc_no);

        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (mPage <= totalPage) {
                    presenter.getApprovalDetail(doc_no, mPage);
                } else {
                    mRefreshLayout.setEnableLoadmore(false);
                }
                mRefreshLayout.finishLoadmore();
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ApprovalAssetAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider2));
        mRecyclerView.addItemDecoration(divider);

        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.rejectButton).setOnClickListener(this);
        findViewById(R.id.agreeButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rejectButton:
                Intent intent = new Intent(this, RejectActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_KEY_APPROVAL_ID, doc_no);
                startActivity(intent);
                break;
            case R.id.agreeButton:
                presenter.getAuditSave(doc_no, 1, "");
                break;
            case R.id.iv_title_left:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.BEDETAIL);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_ASSER_ID, mAssets.get(position).getAsset_id());
        intent.putExtra(Constant.INTENT_IS_HISTORY_ASSER_ID, "0");
        intent.putExtra("webName", "资产详情");
        startActivity(intent);
    }

    @Override
    public void showCodeMsg(String code, String msg) {
        super.showCodeMsg(code, msg);
    }

    @Override
    public void showInvalidType(int invalid_type) {
        super.showInvalidType(invalid_type);
    }

    @Override
    public void showErrorMsg(int status, String msg) {
        super.showErrorMsg(status, msg);
    }

    @Override
    public void showApprovalDetail(ApprovalDetailBean approvalDetail) {
        mAssets.clear();
        List<AssetBean> assets = approvalDetail.getList();
        assetSize = approvalDetail.getList().size();
        assetSizeView.setText("资产明细(" + assetSize + ")");
        mAdapter.addDataSource(assets);
        mAssets.addAll(assets);
        totalPage = approvalDetail.getPage_count();
        if (mPage <= totalPage) {
            mPage = ++mPage;
        }
    }

    @Override
    public void showApprovalMoreDetail(ApprovalDetailBean approvalDetail) {
        List<AssetBean> assets = approvalDetail.getList();
        mAdapter.addDataSource(assets);
        mAssets.addAll(assets);
        totalPage = approvalDetail.getPage_count();
        if (mPage <= totalPage) {
            mPage = ++mPage;
        }
    }

    @Override
    public void showHeadDetail(List<ApprovalHeadDetail> headDetail) {
        if (headDetail != null && headDetail.size() > 0) {
            int headDetailSize = headDetail.size();
            switch (headDetailSize) {
                case 1:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.GONE);
                    layout3.setVisibility(View.GONE);
                    layout4.setVisibility(View.GONE);
                    layout5.setVisibility(View.GONE);
                    layout6.setVisibility(View.GONE);
                    text1_0.setText(headDetail.get(0).getCn());
                    text1_1.setText(headDetail.get(0).getVal());
                    break;
                case 2:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.GONE);
                    layout4.setVisibility(View.GONE);
                    layout5.setVisibility(View.GONE);
                    layout6.setVisibility(View.GONE);
                    text1_0.setText(headDetail.get(0).getCn());
                    text1_1.setText(headDetail.get(0).getVal());
                    text2_0.setText(headDetail.get(1).getCn());
                    text2_1.setText(headDetail.get(1).getVal());
                    break;
                case 3:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.VISIBLE);
                    layout4.setVisibility(View.GONE);
                    layout5.setVisibility(View.GONE);
                    layout6.setVisibility(View.GONE);
                    text1_0.setText(headDetail.get(0).getCn());
                    text1_1.setText(headDetail.get(0).getVal());
                    text2_0.setText(headDetail.get(1).getCn());
                    text2_1.setText(headDetail.get(1).getVal());
                    text3_0.setText(headDetail.get(2).getCn());
                    text3_1.setText(headDetail.get(2).getVal());
                    break;
                case 4:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.VISIBLE);
                    layout4.setVisibility(View.VISIBLE);
                    layout5.setVisibility(View.GONE);
                    layout6.setVisibility(View.GONE);
                    text1_0.setText(headDetail.get(0).getCn());
                    text1_1.setText(headDetail.get(0).getVal());
                    text2_0.setText(headDetail.get(1).getCn());
                    text2_1.setText(headDetail.get(1).getVal());
                    text3_0.setText(headDetail.get(2).getCn());
                    text3_1.setText(headDetail.get(2).getVal());
                    text4_0.setText(headDetail.get(3).getCn());
                    text4_1.setText(headDetail.get(3).getVal());
                    break;
                case 5:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.VISIBLE);
                    layout4.setVisibility(View.VISIBLE);
                    layout5.setVisibility(View.VISIBLE);
                    layout6.setVisibility(View.GONE);
                    text1_0.setText(headDetail.get(0).getCn());
                    text1_1.setText(headDetail.get(0).getVal());
                    text2_0.setText(headDetail.get(1).getCn());
                    text2_1.setText(headDetail.get(1).getVal());
                    text3_0.setText(headDetail.get(2).getCn());
                    text3_1.setText(headDetail.get(2).getVal());
                    text4_0.setText(headDetail.get(3).getCn());
                    text4_1.setText(headDetail.get(3).getVal());
                    text5_0.setText(headDetail.get(4).getCn());
                    text5_1.setText(headDetail.get(4).getVal());
                    break;
                case 6:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.VISIBLE);
                    layout4.setVisibility(View.VISIBLE);
                    layout5.setVisibility(View.VISIBLE);
                    layout6.setVisibility(View.VISIBLE);
                    text1_0.setText(headDetail.get(0).getCn());
                    text1_1.setText(headDetail.get(0).getVal());
                    text2_0.setText(headDetail.get(1).getCn());
                    text2_1.setText(headDetail.get(1).getVal());
                    text3_0.setText(headDetail.get(2).getCn());
                    text3_1.setText(headDetail.get(2).getVal());
                    text4_0.setText(headDetail.get(3).getCn());
                    text4_1.setText(headDetail.get(3).getVal());
                    text5_0.setText(headDetail.get(4).getCn());
                    text5_1.setText(headDetail.get(4).getVal());
                    text6_0.setText(headDetail.get(5).getCn());
                    text6_1.setText(headDetail.get(5).getVal());
                    break;
            }
        }
    }

    @Override
    public void agreeApproval() {
        finish();
    }
}
