package com.gengcon.android.fixedassets.module.inventory.view.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.ResultAsset;
import com.gengcon.android.fixedassets.common.module.scan.ScanInventoryActivity;
import com.gengcon.android.fixedassets.module.base.GApplication;
import com.gengcon.android.fixedassets.module.greendao.AssetBean;
import com.gengcon.android.fixedassets.module.base.BasePullRefreshActivity;
import com.gengcon.android.fixedassets.module.greendao.AssetBeanDao;
import com.gengcon.android.fixedassets.module.inventory.view.InventoryResultView;
import com.gengcon.android.fixedassets.module.inventory.presenter.InventoryResultPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class InventoryResultActivity extends BasePullRefreshActivity implements View.OnClickListener, InventoryResultView {


    private List<AssetBean> mResultList;
    private InventoryResultPresenter mPresenter;
    private String pd_no;
    private String pd_name;
    private int pd_status;
    private int mPage = 1;
    private TextView tv_title_text, tv_title_status, tv_title_right;
    private ImageView pdView;
    private AssetBeanDao assetBeanDao;
    private List<AssetBean> assets;
    private FrameLayout noFinishLayout;
    private List<String> asset_ids;
    private String user_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_result);
        pd_no = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID);
        pd_name = getIntent().getStringExtra("pd_name");
        pd_status = getIntent().getIntExtra("pd_status", -1);
        user_id = (String) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.USER_ID, "");
        if (pd_no == null) {
            finish();
            return;
        }
        asset_ids = new ArrayList<>();
        assetBeanDao = GApplication.getDaoSession().getAssetBeanDao();
        assets = assetBeanDao.queryBuilder()
                .where(AssetBeanDao.Properties.Pd_no.eq(pd_no)).where(
                        AssetBeanDao.Properties.User_id.eq(user_id)).list();
        mResultList = new ArrayList<>();
        mPresenter = new InventoryResultPresenter();
        mPresenter.attachView(this);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        tv_title_text = findViewById(R.id.tv_title_text);
        tv_title_status = findViewById(R.id.tv_title_status);
        tv_title_right = findViewById(R.id.tv_title_right);
        noFinishLayout = findViewById(R.id.noFinishLayout);
        pdView = findViewById(R.id.pdView);
        pdView.setOnClickListener(this);
        tv_title_text.setText(pd_name);
        tv_title_right.setOnClickListener(this);
        findViewById(R.id.syncDataView).setOnClickListener(this);
        findViewById(R.id.auditView).setOnClickListener(this);
        initPdStatus();
        if (assets.size() == 0) {
            mPresenter.showInventoryResult(pd_no, mPage);
        } else {
            if (pd_status == 4 || pd_status == 2) {
                mPresenter.showInventoryResult(pd_no, mPage);
            } else {
                getNoFinishFragment(assets);
            }
        }
    }

    private void initPdStatus() {
        if (pd_status == 1 || pd_status == 3) {
            tv_title_status.setText(R.string.inventory_doing);
            tv_title_status.setBackgroundResource(R.drawable.bg_inventory_doing);
            tv_title_right.setVisibility(View.VISIBLE);
            noFinishLayout.setVisibility(View.VISIBLE);
            pdView.setVisibility(View.VISIBLE);
        } else if (pd_status == 2) {
            tv_title_status.setText(R.string.inventory_wait);
            tv_title_status.setBackgroundResource(R.drawable.bg_inventory_wait);
            tv_title_right.setVisibility(View.GONE);
            noFinishLayout.setVisibility(View.GONE);
            pdView.setVisibility(View.GONE);
        } else {
            tv_title_status.setText(R.string.inventory_finished);
            tv_title_status.setBackgroundResource(R.drawable.bg_inventory_finished);
            tv_title_right.setVisibility(View.GONE);
            noFinishLayout.setVisibility(View.GONE);
            pdView.setVisibility(View.GONE);
        }


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
                long wpCount = assetBeanDao.queryBuilder().where(AssetBeanDao.Properties.User_id.eq(user_id))
                        .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                        .where(AssetBeanDao.Properties.Pd_status.eq(1)).count();
                long ypCount = assetBeanDao.queryBuilder().where(AssetBeanDao.Properties.User_id.eq(user_id))
                        .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                        .where(AssetBeanDao.Properties.Pd_status.eq(2)).count();
                Intent intent = new Intent();
                intent.putExtra("wpCount", wpCount);
                intent.putExtra("ypCount", ypCount);
                setResult(Constant.RESULT_OK_INVENTORY_PD_NUM, intent);
                finish();
                break;
            case R.id.tv_title_right:

                break;
            case R.id.syncDataView:
                List<AssetBean> hasScanAsset = assetBeanDao.queryBuilder().where(assetBeanDao.queryBuilder().and(AssetBeanDao.Properties.Pd_no.eq(pd_no), AssetBeanDao.Properties.IsScanAsset.eq(1))).list();
                if (hasScanAsset.size() > 0) {
                    for (int i = 0; i < hasScanAsset.size(); i++) {
                        if (!asset_ids.contains(hasScanAsset.get(i).getAsset_id())) {
                            asset_ids.add(hasScanAsset.get(i).getAsset_id());
                        }
                    }
                }
                if (asset_ids.size() == 0) {
                    mPage = 1;
                    mPresenter.showInventoryResult(pd_no, mPage);
                } else {
                    mPresenter.showSyncAssetData(pd_no, asset_ids);
                }
                break;
            case R.id.auditView:
                break;
            case R.id.pdView:
                Intent intentScan = new Intent(this, ScanInventoryActivity.class);
                intentScan.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, pd_no);
                intentScan.putExtra("user_id", user_id);
                startActivityForResult(intentScan, Constant.REQUEST_CODE_INVENTORY_SCAN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constant.REQUEST_CODE_INVENTORY_SCAN && resultCode == Constant.RESULT_OK_INVENTORY_SCAN) {
            assets = assetBeanDao.queryBuilder().where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                    .where(AssetBeanDao.Properties.User_id.eq(user_id)).list();
            getNoFinishFragment(assets);
        }
    }

    private void getNoFinishFragment(List<AssetBean> assetBeans) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        InventoryNoFinishFragment noFinishFragment = new InventoryNoFinishFragment(assetBeans);
        ft.replace(R.id.fl, noFinishFragment);
        ft.commitAllowingStateLoss();
    }

    private void getFinishedFragment(List<AssetBean> assetBeans) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        InventoryFinishedFragment finishedFragment = new InventoryFinishedFragment(assetBeans);
        ft.replace(R.id.fl, finishedFragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void showInventoryResult(ResultAsset resultAsset) {
        if (mPage == 1) {
            mResultList.clear();
            mResultList.addAll(resultAsset.getList());
        } else {
            mResultList.addAll(resultAsset.getList());
        }
        if (mPage < resultAsset.getPage_count()) {
            mPage++;
            mPresenter.showInventoryResult(pd_no, mPage);
        }
        if (resultAsset.getCurrent_page() == resultAsset.getPage_count()) {
            for (int i = 0; i < mResultList.size(); i++) {
                mResultList.get(i).setPd_no(pd_no);
                mResultList.get(i).setUser_id(user_id);
            }
            assetBeanDao.deleteInTx(assetBeanDao.queryBuilder()
                    .where(assetBeanDao.queryBuilder()
                            .and(AssetBeanDao.Properties.Pd_no.eq(pd_no),
                                    AssetBeanDao.Properties.User_id.eq(user_id))).list());
            assetBeanDao.insertInTx(mResultList);
            if (pd_status == 4) {
                getFinishedFragment(mResultList);
            } else if (pd_status == 1 || pd_status == 2 || pd_status == 3) {
                getNoFinishFragment(mResultList);
            }
        }
    }

    @Override
    public void syncAssetSuccess() {
        asset_ids.clear();
        ToastUtils.toastMessage(this, "同步数据成功");
        mPage = 1;
        mPresenter.showInventoryResult(pd_no, mPage);
    }

    @Override
    public void syncAssetFailed(int type) {
        if (type == 1) {
            List<AssetBean> isDeleteAsset = assetBeanDao.queryBuilder().where(AssetBeanDao.Properties.Pd_no.eq(pd_no)).list();
            assetBeanDao.deleteInTx(isDeleteAsset);
            finish();
        } else if (type == 2) {
            pd_status = 2;
            initPdStatus();
            mPage = 1;
            mPresenter.showInventoryResult(pd_no, mPage);
        } else if (type == 3) {
            pd_status = 4;
            initPdStatus();
            mPage = 1;
            mPresenter.showInventoryResult(pd_no, 1);
        }
    }
}
