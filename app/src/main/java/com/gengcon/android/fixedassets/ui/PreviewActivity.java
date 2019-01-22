package com.gengcon.android.fixedassets.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.adapter.AssetAdapter;
import com.gengcon.android.fixedassets.adapter.OnItemClickListener;
import com.gengcon.android.fixedassets.bean.AssetBean;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.PreviewInfo;
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.presenter.PreviewInfoPresenter;
import com.gengcon.android.fixedassets.presenter.UploadInventoryPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.RolePowerManager;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.PreviewInfoView;
import com.gengcon.android.fixedassets.view.UploadInventoryView;
import com.gengcon.android.fixedassets.widget.AlertDialog;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.List;

public class PreviewActivity extends BasePullRefreshActivity implements View.OnClickListener, OnItemClickListener, PreviewInfoView, UploadInventoryView {

    private RefreshLayout mRefreshLayout;
    private MyRecyclerView mRecyclerView;
    private AssetAdapter mAdapter;
    private TextView mTvSize, mTvNormal, mTvEarn, mTvLoss, mTvInvalid;
    private PreviewInfoPresenter mPreviewPresenter;
    private UploadInventoryPresenter mUploadPresenter;
    private PreviewInfo mResultPreviewInfo;
    private String mInventoryId;
    private List<String> mAssetIds;
    private int mPage = 1;
    private int mSelect = AssetBean.INVENTORY_NORMAL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        initView();
        mInventoryId = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID);
        mAssetIds = (List<String>) getIntent().getSerializableExtra(Constant.INTENT_EXTRA_KEY_ASSET_IDS);
        mPreviewPresenter = new PreviewInfoPresenter();
        mUploadPresenter = new UploadInventoryPresenter();
        mPreviewPresenter.attachView(this);
        mUploadPresenter.attachView(this);
        if (mInventoryId == null || mAssetIds == null || mAssetIds.size() <= 0) {
            finish();
            return;
        }
        initSelect();
    }

    @Override
    protected void initView() {
        super.initView();
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.inventory_preview);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_right)).setText(R.string.upload);

        mTvSize = findViewById(R.id.tv_size);
        mTvNormal = findViewById(R.id.tv_normal);
        mTvEarn = findViewById(R.id.tv_earn);
        mTvLoss = findViewById(R.id.tv_loss);
        mTvInvalid = findViewById(R.id.tv_invalid);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AssetAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider2));
        mRecyclerView.addItemDecoration(divider);

        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPreviewPresenter.previewInfo(mInventoryId, mSelect, ++mPage, mAssetIds);
            }
        });

        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.tv_title_right).setOnClickListener(this);
        mTvNormal.setOnClickListener(this);
        mTvEarn.setOnClickListener(this);
        mTvLoss.setOnClickListener(this);

//        if (!RolePowerManager.getInstance().checkInventoryPower(getString(R.string.upload))) {
//            findViewById(R.id.tv_title_right).setVisibility(View.GONE);
//        }
    }

    private void initSelect() {
        mRefreshLayout.finishLoadmore();
        mTvNormal.setTextColor(getResources().getColor(R.color.black_text));
        mTvEarn.setTextColor(getResources().getColor(R.color.black_text));
        mTvLoss.setTextColor(getResources().getColor(R.color.black_text));
        if (mSelect == AssetBean.INVENTORY_NORMAL) {
            mTvNormal.setTextColor(getResources().getColor(R.color.blue));
        } else if (mSelect == AssetBean.INVENTORY_EARN) {
            mTvEarn.setTextColor(getResources().getColor(R.color.blue));
        } else if (mSelect == AssetBean.INVENTORY_LOSS) {
            mTvLoss.setTextColor(getResources().getColor(R.color.blue));
        }
        mPage = 1;
        mPreviewPresenter.previewInfo(mInventoryId, mSelect, mPage, mAssetIds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPreviewPresenter.detachView();
        mUploadPresenter.detachView();
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
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.tv_title_right:
                if (RolePowerManager.getInstance().isInventoryPd()) {
                    showUploadDialog();
                } else {
                    showPermissionDeniedTips();
                }
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
        intent.putExtra(Constant.INTENT_EXTRA_KEY_ASSER_ID, mAdapter.getAssets().get(position).getAsset_id());
        intent.putExtra(Constant.INTENT_IS_HISTORY_ASSER_ID, "0");
        intent.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, mInventoryId);
        startActivity(intent);
    }

    private void showUploadDialog() {
        SpannableString spannableString = new SpannableString(getString(R.string.pending_inventory_assets) + (mResultPreviewInfo.getBase_data().getLoss_num() + mResultPreviewInfo.getBase_data().getNormal_num()) + getString(R.string.item) + "\r\n"
                + getString(R.string.actual_inventory_size) + mAssetIds.size() + getString(R.string.item) + "\r\n"
                + getString(R.string.can_upload));
        AlertDialog.Builder builder = new AlertDialog.Builder(this, false);
        builder.setTitle(R.string.tips);
        builder.setText(spannableString.toString());
        builder.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, getString(R.string.cancel));
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mUploadPresenter.uploadInventoryResult(mInventoryId, mAssetIds);
            }
        }, getString(R.string.confirm));
        builder.show();
    }

    @Override
    public void uploadResult(Bean resultBean) {
        ToastUtils.toastMessage(this, resultBean.getMsg());
        Intent intent = new Intent(this, InventoryListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showPreviewInfo(PreviewInfo previewInventoryInfo) {
        mResultPreviewInfo = previewInventoryInfo;
        mTvSize.setText(getString(R.string.actual_check_size) + ":(" + (previewInventoryInfo.getBase_data().getNormal_num()
                + previewInventoryInfo.getBase_data().getSurplus_num() + previewInventoryInfo.getBase_data().getInvalid_num()) + ")");
        mTvLoss.setText(getString(R.string.inventory_loss) + "(" + previewInventoryInfo.getBase_data().getLoss_num() + ")、");
        mTvEarn.setText(getString(R.string.inventory_earn) + "(" + previewInventoryInfo.getBase_data().getSurplus_num() + ")、");
        mTvNormal.setText(getString(R.string.normal) + "(" + previewInventoryInfo.getBase_data().getNormal_num() + ")、");
        mTvInvalid.setText(getString(R.string.invalid) + "(" + previewInventoryInfo.getBase_data().getInvalid_num() + ")");
        if (mPage == 1) {
            mAdapter.changeDataSource(previewInventoryInfo.getAsset_data().getList());
        } else {
            mAdapter.addDataSource(previewInventoryInfo.getAsset_data().getList());
            mRefreshLayout.finishLoadmore();
        }
        if (mPage >= previewInventoryInfo.getAsset_data().getPage_count()) {
            mRefreshLayout.setEnableLoadmore(false);
        } else {
            mRefreshLayout.setEnableLoadmore(true);
        }
        initDefault(mPage == 1 ? (previewInventoryInfo.getAsset_data().getList() != null && previewInventoryInfo.getAsset_data().getList().size() != 0) ? NORMAL : NO_DATA : NORMAL);
    }
}
