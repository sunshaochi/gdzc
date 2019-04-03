package com.gengcon.android.fixedassets.module.inventory.view.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.inventory.widget.adapter.AssetAdapter;
import com.gengcon.android.fixedassets.common.OnItemClickListener;
import com.gengcon.android.fixedassets.bean.AssetBean;
import com.gengcon.android.fixedassets.bean.result.InventoryR;
import com.gengcon.android.fixedassets.common.module.htttp.URL;
import com.gengcon.android.fixedassets.module.base.BasePullRefreshActivity;
import com.gengcon.android.fixedassets.module.inventory.view.InventoryResultView;
import com.gengcon.android.fixedassets.module.inventory.presenter.InventoryResultPresenter;
import com.gengcon.android.fixedassets.module.web.view.WebActivity;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

public class InventoryResultActivity extends BasePullRefreshActivity implements View.OnClickListener, OnItemClickListener, InventoryResultView {

    private RefreshLayout mRefreshLayout;
    private MyRecyclerView mRecyclerView;
    private AssetAdapter mAdapter;
    private TextView mTvNormal, mTvEarn, mTvLoss;
    private View mVNormal, mVEarn, mVLoss;
    private int mSelect = AssetBean.INVENTORY_NORMAL;

    private InventoryR mResultInventoryR;
    private List<AssetBean> mResultList;
    private InventoryResultPresenter mPresenter;
    private String mInventoryId;
    private int mPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_result);
        mInventoryId = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID);
        if (mInventoryId == null) {
            finish();
            return;
        }
        mResultList = new ArrayList<>();
        mPresenter = new InventoryResultPresenter();
        mPresenter.attachView(this);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.inventory_result);

        mTvNormal = findViewById(R.id.tv_normal);
        mTvEarn = findViewById(R.id.tv_earn);
        mTvLoss = findViewById(R.id.tv_loss);
        mVNormal = findViewById(R.id.v_normal);
        mVEarn = findViewById(R.id.v_earn);
        mVLoss = findViewById(R.id.v_loss);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AssetAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);
        mAdapter.setPadding(true);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider1));
        mRecyclerView.addItemDecoration(divider);

        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.showInventoryResult(mInventoryId, mSelect, ++mPage);
                refreshlayout.finishLoadmore();
            }
        });

        findViewById(R.id.iv_title_left).setOnClickListener(this);
        mTvNormal.setOnClickListener(this);
        mTvEarn.setOnClickListener(this);
        mTvLoss.setOnClickListener(this);
        initSelect();
    }

    private void initSelect() {
        mRefreshLayout.finishLoadmore();
        mTvNormal.setTextColor(getResources().getColor(R.color.black_text));
        mVNormal.setBackgroundColor(getResources().getColor(R.color.white));
        mTvEarn.setTextColor(getResources().getColor(R.color.black_text));
        mVEarn.setBackgroundColor(getResources().getColor(R.color.white));
        mTvLoss.setTextColor(getResources().getColor(R.color.black_text));
        mVLoss.setBackgroundColor(getResources().getColor(R.color.white));
        if (mSelect == AssetBean.INVENTORY_NORMAL) {
            mTvNormal.setTextColor(getResources().getColor(R.color.blue));
            mVNormal.setBackgroundColor(getResources().getColor(R.color.blue));
        } else if (mSelect == AssetBean.INVENTORY_EARN) {
            mTvEarn.setTextColor(getResources().getColor(R.color.blue));
            mVEarn.setBackgroundColor(getResources().getColor(R.color.blue));
        } else if (mSelect == AssetBean.INVENTORY_LOSS) {
            mTvLoss.setTextColor(getResources().getColor(R.color.blue));
            mVLoss.setBackgroundColor(getResources().getColor(R.color.blue));
        }
        mPage = 1;
        mPresenter.showInventoryResult(mInventoryId, mSelect, mPage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
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
    public void showInvalidType(int invalid_type) {
        super.showInvalidType(invalid_type);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.tv_normal:
                if (mSelect != AssetBean.INVENTORY_NORMAL) {
                    mSelect = AssetBean.INVENTORY_NORMAL;
                    initSelect();
                }
                break;
            case R.id.tv_earn:
                if (mSelect != AssetBean.INVENTORY_EARN) {
                    mSelect = AssetBean.INVENTORY_EARN;
                    initSelect();
                }
                break;
            case R.id.tv_loss:
                if (mSelect != AssetBean.INVENTORY_LOSS) {
                    mSelect = AssetBean.INVENTORY_LOSS;
                    initSelect();
                }
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.BEDETAIL);
        intent.putExtra("webName", "资产详情");
        intent.putExtra(Constant.INTENT_EXTRA_KEY_ASSER_ID, mResultList.get(position).getAsset_id());
        intent.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, mInventoryId);
        intent.putExtra(Constant.INTENT_IS_HISTORY_ASSER_ID, "1");
        startActivity(intent);
    }

    @Override
    public void showInventoryResult(InventoryR inventoryR) {
        mRefreshLayout.finishLoadmore();
        mResultInventoryR = inventoryR;
        mTvNormal.setText(getString(R.string.normal) + "(" + inventoryR.getBase_data().getNormal_num() + ")");
        mTvLoss.setText(getString(R.string.inventory_loss) + "(" + inventoryR.getBase_data().getLoss_num() + ")");
        mTvEarn.setText(getString(R.string.inventory_earn) + "(" + inventoryR.getBase_data().getSurplus_num() + ")");
        if (mPage != 1) {
            mAdapter.addDataSource(inventoryR.getAsset_data().getList());
            mResultList.addAll(inventoryR.getAsset_data().getList());
        } else {
            mAdapter.changeDataSource(inventoryR.getAsset_data().getList());
            mResultList.clear();
            mResultList.addAll(inventoryR.getAsset_data().getList());
        }
        if (inventoryR.getAsset_data().getPage_count() <= mPage) {
            mRefreshLayout.setEnableLoadmore(false);
        } else {
            mRefreshLayout.setEnableLoadmore(true);
        }
        initDefault(mPage == 1 ? (mResultInventoryR.getAsset_data().getList() != null && mResultInventoryR.getAsset_data().getList().size() != 0) ? NORMAL : NO_DATA : NORMAL);
    }
}
