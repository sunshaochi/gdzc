package com.gengcon.android.fixedassets.module.approval.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.Asset;
import com.gengcon.android.fixedassets.module.approval.view.ApprovalDetailView;
import com.gengcon.android.fixedassets.module.approval.widget.adapter.ApprovalAssetAdapter;
import com.gengcon.android.fixedassets.common.OnItemClickListener;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.ApprovalDetailBean;
import com.gengcon.android.fixedassets.bean.result.ApprovalHeadDetail;
import com.gengcon.android.fixedassets.common.module.http.URL;
import com.gengcon.android.fixedassets.module.approval.presenter.ApprovalDetailPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.DensityUtils;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ApprovalDetailActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener, ApprovalDetailView {

    private TextView text1_0, text1_1,
            text2_0, text2_1,
            text3_0, text3_1,
            text4_0, text4_1,
            text5_0, text5_1, assetSizeView;
    private LinearLayout layout1, layout2, layout3, layout4, layout5;
    private TextView tvName;
    private TextView tvStatus;
    private TextView tvOrder;
    private TextView applyName;
    private TextView applyTime;
    private TextView approvalName;
    private TextView approvalTime;
    private TextView statusView;
    private TextView tv_left;
    private LinearLayout approvalLayout;
    private LinearLayout resultLayout;
    private LinearLayout rejectOrAgreeLayout;
    private RefreshLayout mRefreshLayout;
    private MyRecyclerView mRecyclerView;
    private ApprovalAssetAdapter mAdapter;
    private ArrayList<Asset> mAssets;
    private View header;
    private int mPage = 1;

    public static int RESULT_OK = 1111;
    public static int REQUEST_CODE = 1110;

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

    private void setHeader(RecyclerView view) {
        header = LayoutInflater.from(this).inflate(R.layout.activity_detail_approval_header, view, false);
        mAdapter.setHeaderView(header);
    }

    @Override
    protected void initView() {
        tv_left = findViewById(R.id.tv_title_text);
        if (doc_type == 1) {
            ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.wait_detail);
        } else if (doc_type == 2) {
            ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.complete_detail);
        }
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
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
        mAdapter = new ApprovalAssetAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        setHeader(mRecyclerView);
        mAdapter.setItemClickListener(this);
        text1_0 = header.findViewById(R.id.text1_0);
        text1_1 = header.findViewById(R.id.text1_1);
        text2_0 = header.findViewById(R.id.text2_0);
        text2_1 = header.findViewById(R.id.text2_1);
        text3_0 = header.findViewById(R.id.text3_0);
        text3_1 = header.findViewById(R.id.text3_1);
        text4_0 = header.findViewById(R.id.text4_0);
        text4_1 = header.findViewById(R.id.text4_1);
        text5_0 = header.findViewById(R.id.text5_0);
        text5_1 = header.findViewById(R.id.text5_1);
        layout1 = header.findViewById(R.id.layout1);
        layout2 = header.findViewById(R.id.layout2);
        layout3 = header.findViewById(R.id.layout3);
        layout4 = header.findViewById(R.id.layout4);
        layout5 = header.findViewById(R.id.layout5);
        tvName = header.findViewById(R.id.tv_name);
        tvStatus = header.findViewById(R.id.tv_status);
        tvOrder = header.findViewById(R.id.tv_order);
        applyName = header.findViewById(R.id.apply_name);
        applyTime = header.findViewById(R.id.apply_time);
        statusView = header.findViewById(R.id.statusView);
        approvalTime = header.findViewById(R.id.approval_time);
        approvalName = header.findViewById(R.id.approval_name);
        approvalLayout = header.findViewById(R.id.approvalLayout);
        resultLayout = header.findViewById(R.id.resultLayout);
        assetSizeView = header.findViewById(R.id.assetSizeView);
        rejectOrAgreeLayout = findViewById(R.id.rejectOrAgreeLayout);

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
                startActivityForResult(intent, REQUEST_CODE);
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
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                mPage = 1;
                mRefreshLayout.setEnableLoadmore(true);
                presenter.getDetailHead(doc_no);
                presenter.getApprovalDetail(doc_no, 1);
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, AssetDetailsActivity.class);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.APPROVALASSETDETAIL + mAssets.get(position).getAsset_id() + "&doc_no=" + doc_no);
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
        List<Asset> assets = approvalDetail.getList();
        assetSize = approvalDetail.getTotal();
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
        List<Asset> assets = approvalDetail.getList();
        mAdapter.addMoreDataSource(assets);
        mAssets.addAll(assets);
        totalPage = approvalDetail.getPage_count();
        if (mPage <= totalPage) {
            mPage = ++mPage;
        }
    }

    @Override
    public void showHeadDetail(ApprovalHeadDetail headDetail) {
        tvName.setText(headDetail.getAudit_info().getDoc_type_cn());
        tvOrder.setText(headDetail.getAudit_info().getDoc_no());
        tvStatus.setText(headDetail.getAudit_info().getStatus_cn());
        applyName.setText(headDetail.getAudit_info().getCreated_username());
        applyTime.setText(headDetail.getAudit_info().getCreated_at());
        tvStatus.setTextColor(getResources().getColor(headDetail.getAudit_info().getStatus() == ApprovalHeadDetail.REJECT ? R.color.approval_reject : headDetail.getAudit_info().getStatus() == ApprovalHeadDetail.AGREE ? R.color.approval_agree : R.color.approval_wait));
        tvStatus.setBackgroundResource(headDetail.getAudit_info().getStatus() == ApprovalHeadDetail.REJECT ? R.drawable.approval_reject : headDetail.getAudit_info().getStatus() == ApprovalHeadDetail.AGREE ? R.drawable.approval_agree : R.drawable.approval_wait);
        switch (headDetail.getAudit_info().getStatus()) {
            case 1:
                mRecyclerView.setPadding(0, 0, 0, DensityUtils.dp2px(this, 40));
                rejectOrAgreeLayout.setVisibility(View.VISIBLE);
                approvalLayout.setVisibility(View.GONE);
                resultLayout.setVisibility(View.GONE);
                tv_left.setText(R.string.wait_detail);
                break;
            case 2:
            case 3:
                String status = headDetail.getAudit_info().getStatus_cn();
                String reason = "";
                if (!TextUtils.isEmpty(headDetail.getAudit_info().getReason())) {
                    reason = headDetail.getAudit_info().getReason();
                }
                SpannableString spannableString = new SpannableString(status + "  " + reason);
                if (headDetail.getAudit_info().getStatus() == ApprovalHeadDetail.REJECT) {
                    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.approval_reject)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if (headDetail.getAudit_info().getStatus() == ApprovalHeadDetail.AGREE) {
                    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.approval_agree)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_text)), 3, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                statusView.setText(spannableString);
                mRecyclerView.setPadding(0, 0, 0, 0);
                tv_left.setText(R.string.complete_detail);
                approvalLayout.setVisibility(View.VISIBLE);
                rejectOrAgreeLayout.setVisibility(View.GONE);
                resultLayout.setVisibility(View.VISIBLE);
                approvalName.setText(headDetail.getAudit_info().getAuditor_name());
                approvalTime.setText(headDetail.getAudit_info().getAuditor_at());
                break;
        }
        if (headDetail.getBaseInfo().size() > 0) {
            int headDetailSize = headDetail.getBaseInfo().size();
            switch (headDetailSize) {
                case 1:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.GONE);
                    layout3.setVisibility(View.GONE);
                    layout4.setVisibility(View.GONE);
                    layout5.setVisibility(View.GONE);
                    text1_0.setText(headDetail.getBaseInfo().get(0).getCn());
                    text1_1.setText(headDetail.getBaseInfo().get(0).getVal());
                    break;
                case 2:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.GONE);
                    layout4.setVisibility(View.GONE);
                    layout5.setVisibility(View.GONE);
                    text1_0.setText(headDetail.getBaseInfo().get(0).getCn());
                    text1_1.setText(headDetail.getBaseInfo().get(0).getVal());
                    text2_0.setText(headDetail.getBaseInfo().get(1).getCn());
                    text2_1.setText(headDetail.getBaseInfo().get(1).getVal());
                    break;
                case 3:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.VISIBLE);
                    layout4.setVisibility(View.GONE);
                    layout5.setVisibility(View.GONE);
                    text1_0.setText(headDetail.getBaseInfo().get(0).getCn());
                    text1_1.setText(headDetail.getBaseInfo().get(0).getVal());
                    text2_0.setText(headDetail.getBaseInfo().get(1).getCn());
                    text2_1.setText(headDetail.getBaseInfo().get(1).getVal());
                    text3_0.setText(headDetail.getBaseInfo().get(2).getCn());
                    text3_1.setText(headDetail.getBaseInfo().get(2).getVal());
                    break;
                case 4:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.VISIBLE);
                    layout4.setVisibility(View.VISIBLE);
                    layout5.setVisibility(View.GONE);
                    text1_0.setText(headDetail.getBaseInfo().get(0).getCn());
                    text1_1.setText(headDetail.getBaseInfo().get(0).getVal());
                    text2_0.setText(headDetail.getBaseInfo().get(1).getCn());
                    text2_1.setText(headDetail.getBaseInfo().get(1).getVal());
                    text3_0.setText(headDetail.getBaseInfo().get(2).getCn());
                    text3_1.setText(headDetail.getBaseInfo().get(2).getVal());
                    text4_0.setText(headDetail.getBaseInfo().get(3).getCn());
                    text4_1.setText(headDetail.getBaseInfo().get(3).getVal());
                    break;
                case 5:
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.VISIBLE);
                    layout4.setVisibility(View.VISIBLE);
                    layout5.setVisibility(View.VISIBLE);
                    text1_0.setText(headDetail.getBaseInfo().get(0).getCn());
                    text1_1.setText(headDetail.getBaseInfo().get(0).getVal());
                    text2_0.setText(headDetail.getBaseInfo().get(1).getCn());
                    text2_1.setText(headDetail.getBaseInfo().get(1).getVal());
                    text3_0.setText(headDetail.getBaseInfo().get(2).getCn());
                    text3_1.setText(headDetail.getBaseInfo().get(2).getVal());
                    text4_0.setText(headDetail.getBaseInfo().get(3).getCn());
                    text4_1.setText(headDetail.getBaseInfo().get(3).getVal());
                    text5_0.setText(headDetail.getBaseInfo().get(4).getCn());
                    text5_1.setText(headDetail.getBaseInfo().get(4).getVal());
                    break;
            }
        }
    }

    @Override
    public void agreeApproval() {
        mRefreshLayout.setEnableLoadmore(true);
        mPage = 1;
        presenter.getApprovalDetail(doc_no, mPage);
        presenter.getDetailHead(doc_no);
    }

    @Override
    public void contractExpire() {
        showContractExpireDialog();
    }

}
