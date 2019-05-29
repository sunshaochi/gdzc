package com.gengcon.android.fixedassets.module.inventory.view.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import pub.devrel.easypermissions.EasyPermissions;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iscandemo.ScannerInerface;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.ResultAsset;
import com.gengcon.android.fixedassets.common.module.scan.ScanInventoryActivity;
import com.gengcon.android.fixedassets.module.base.GApplication;
import com.gengcon.android.fixedassets.module.greendao.AssetBean;
import com.gengcon.android.fixedassets.module.base.BasePullRefreshActivity;
import com.gengcon.android.fixedassets.module.greendao.AssetBeanDao;
import com.gengcon.android.fixedassets.module.greendao.InventoryBean;
import com.gengcon.android.fixedassets.module.greendao.InventoryBeanDao;
import com.gengcon.android.fixedassets.module.inventory.view.InventoryResultView;
import com.gengcon.android.fixedassets.module.inventory.presenter.InventoryResultPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.widget.AlertDialog;
import com.gengcon.android.fixedassets.widget.InfraredDialog;

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
    private LinearLayout statusLayout;
    private ImageView pdView;
    private AssetBeanDao assetBeanDao;
    private InventoryBeanDao inventoryBeanDao;
    private List<AssetBean> assets;
    private FrameLayout noFinishLayout;
    private List<String> asset_ids;
    private List<String> audit_asset_ids;
    private String user_id;
    private int isUpdate;

    private InfraredDialog infraredDialog;
    private ScannerInerface mControll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_result);
        pd_no = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID);
        pd_name = getIntent().getStringExtra("pd_name");
        pd_status = getIntent().getIntExtra("pd_status", -1);
        isUpdate = getIntent().getIntExtra("is_update", -1);
        user_id = (String) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.USER_ID, "");
        if (pd_no == null) {
            finish();
            return;
        }
        asset_ids = new ArrayList<>();
        audit_asset_ids = new ArrayList<>();
        assetBeanDao = GApplication.getDaoSession().getAssetBeanDao();
        assets = assetBeanDao.queryBuilder()
                .where(AssetBeanDao.Properties.Pd_no.eq(pd_no)).where(
                        AssetBeanDao.Properties.User_id.eq(user_id)).list();
        inventoryBeanDao = GApplication.getDaoSession().getInventoryBeanDao();
        mResultList = new ArrayList<>();
        mPresenter = new InventoryResultPresenter();
        mPresenter.attachView(this);

        mControll = new ScannerInerface(this);
        mControll.setOutputMode(1);
        registerReceiver(mScanReceiver, new IntentFilter("android.intent.action.SCANRESULT"));
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
        statusLayout = findViewById(R.id.statusLayout);
        pdView = findViewById(R.id.pdView);
        pdView.setOnClickListener(this);
        tv_title_text.setText(pd_name);
        tv_title_right.setOnClickListener(this);
        findViewById(R.id.syncDataView).setOnClickListener(this);
        findViewById(R.id.auditView).setOnClickListener(this);
        initPdStatus();
        if (assets.size() == 0) {
            if (!isNetworkConnected(this)) {
                if (pd_status == 4) {
                    getFinishedFragment(assets);
                } else {
                    getNoFinishFragment(assets);
                }
            } else {
                mPresenter.showInventoryResult(pd_no, mPage);
            }
        } else {
            if (pd_status == 4) {
                if (isUpdate != 1) {
                    if (!isNetworkConnected(this)) {
                        getFinishedFragment(assets);
                    } else {
                        mPresenter.showInventoryResult(pd_no, mPage);
                    }
                } else {
                    getFinishedFragment(assets);
                }
            } else if (pd_status == 2) {
                if (isUpdate != 1) {
                    if (!isNetworkConnected(this)) {
                        getNoFinishFragment(assets);
                    } else {
                        mPresenter.showInventoryResult(pd_no, mPage);
                    }
                } else {
                    getNoFinishFragment(assets);
                }
            } else {
                getNoFinishFragment(assets);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mControll.open();
    }

    private void initPdStatus() {
        if (pd_status == 1 || pd_status == 3) {
            tv_title_status.setText(R.string.inventory_doing);
            statusLayout.setBackgroundResource(R.drawable.bg_inventory_doing);
            tv_title_right.setVisibility(View.VISIBLE);
            noFinishLayout.setVisibility(View.VISIBLE);
            pdView.setVisibility(View.VISIBLE);
        } else if (pd_status == 2) {
            tv_title_status.setText(R.string.inventory_wait);
            statusLayout.setBackgroundResource(R.drawable.bg_inventory_wait);
            tv_title_right.setVisibility(View.GONE);
            noFinishLayout.setVisibility(View.GONE);
            pdView.setVisibility(View.GONE);
        } else {
            tv_title_status.setText(R.string.inventory_finished);
            statusLayout.setBackgroundResource(R.drawable.bg_inventory_finished);
            tv_title_right.setVisibility(View.GONE);
            noFinishLayout.setVisibility(View.GONE);
            pdView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mControll.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScanReceiver);
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
            case R.id.tv_title_right:
                Intent intentRemarks = new Intent(this, InventoryRemarksActivity.class);
                intentRemarks.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, pd_no);
                startActivity(intentRemarks);
                break;
            case R.id.syncDataView:
                List<AssetBean> hasScanAsset = assetBeanDao.queryBuilder()
                        .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                        .where(AssetBeanDao.Properties.IsScanAsset.eq(1)).list();
                if (hasScanAsset.size() > 0) {
                    for (int i = 0; i < hasScanAsset.size(); i++) {
                        if (!asset_ids.contains(hasScanAsset.get(i).getAsset_id())) {
                            asset_ids.add(hasScanAsset.get(i).getAsset_id());
                        }
                    }
                }
                if (isNetworkConnected(this)) {
                    mPresenter.showSyncAssetData(pd_no, asset_ids);
                } else {
                    ToastUtils.toastMessage(this, "网络异常，请稍后重试");
                }
                break;
            case R.id.auditView:
                List<AssetBean> scanAsset = assetBeanDao.queryBuilder()
                        .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                        .where(AssetBeanDao.Properties.IsScanAsset.eq(1)).list();
                if (scanAsset.size() > 0) {
                    for (int i = 0; i < scanAsset.size(); i++) {
                        if (!audit_asset_ids.contains(scanAsset.get(i).getAsset_id())) {
                            audit_asset_ids.add(scanAsset.get(i).getAsset_id());
                        }
                    }
                }
                InventoryBean inventory = inventoryBeanDao.queryBuilder()
                        .where(InventoryBeanDao.Properties.User_id.eq(user_id))
                        .where(InventoryBeanDao.Properties.Pd_no.eq(pd_no)).unique();
                String remarks = inventory.getRemarks();
                if (isNetworkConnected(this)) {
                    mPresenter.auditAssetData(pd_no, remarks, audit_asset_ids);
                } else {
                    ToastUtils.toastMessage(this, "网络异常，请稍后重试");
                }
                break;
            case R.id.pdView:
                methodRequiresCameraPermission();
                break;
        }
    }

    BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String scanResult = intent.getStringExtra("value");
            String result = scanResult.replaceAll(" ", "");
            if (!TextUtils.isEmpty(result)) {
                if (pd_status == 1 || pd_status == 3) {
                    if (result.length() == 24) {
                        AssetBean asset = assetBeanDao.queryBuilder()
                                .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                                .where(AssetBeanDao.Properties.User_id.eq(user_id))
                                .where(AssetBeanDao.Properties.Asset_id.eq(result))
                                .unique();
                        if (asset != null) {
                            if (asset.getPd_status() == 1) {
                                asset.setPd_status(2);
                                assetBeanDao.update(asset);
                                ToastUtils.toastMessage(InventoryResultActivity.this, "盘点成功");
                                showInfraredDialog();
                            } else {
                                ToastUtils.toastMessage(InventoryResultActivity.this, "该资产已盘点");
                            }
                        } else {
                            ToastUtils.toastMessage(InventoryResultActivity.this, "未查询到此资产");
                        }
                    } else {
                        ToastUtils.toastMessage(InventoryResultActivity.this, "非精臣固定资产有效二维码");
                    }
                } else {
                    ToastUtils.toastMessage(InventoryResultActivity.this, "该盘点单已提交");

                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        InventoryBean inventoryUpdate = inventoryBeanDao.queryBuilder()
                .where(InventoryBeanDao.Properties.User_id.eq(user_id))
                .where(InventoryBeanDao.Properties.Pd_no.eq(pd_no)).unique();
        long wpCount = assetBeanDao.queryBuilder().where(AssetBeanDao.Properties.User_id.eq(user_id))
                .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                .where(AssetBeanDao.Properties.Pd_status.eq(1)).count();
        long ypCount = assetBeanDao.queryBuilder().where(AssetBeanDao.Properties.User_id.eq(user_id))
                .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                .where(AssetBeanDao.Properties.Pd_status.eq(2)).count();
        if (wpCount == 0 && ypCount == 0) {
            finish();
        } else {
            inventoryUpdate.setWp_num((int) wpCount);
            inventoryUpdate.setYp_num((int) ypCount);
            inventoryBeanDao.update(inventoryUpdate);
            finish();
        }
        super.onBackPressed();
    }

    private void showInfraredDialog() {
        long noFinishAssetCount = assetBeanDao.queryBuilder()
                .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                .where(AssetBeanDao.Properties.User_id.eq(user_id))
                .where(AssetBeanDao.Properties.Pd_status.eq(1)).count();
        long finishAssetCount = assetBeanDao.queryBuilder()
                .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                .where(AssetBeanDao.Properties.User_id.eq(user_id))
                .where(AssetBeanDao.Properties.Pd_status.eq(2)).count();
        if (infraredDialog == null) {
            infraredDialog = new InfraredDialog(InventoryResultActivity.this).builder();
        }
        infraredDialog.setWpNum((int) noFinishAssetCount);
        infraredDialog.setYpNum(finishAssetCount + "");
        infraredDialog.setManualClick(new InfraredDialog.ManualListener() {
            @Override
            public void onClick() {

            }
        });

        infraredDialog.setCompleteClick(new InfraredDialog.CompleteListener() {
            @Override
            public void onClick() {
                assets = assetBeanDao.queryBuilder().where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                        .where(AssetBeanDao.Properties.User_id.eq(user_id)).list();
                getNoFinishFragment(assets);
                infraredDialog.dismiss();
                infraredDialog = null;
            }
        });
        if (!infraredDialog.isShowing()) {
            infraredDialog.show();
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
        InventoryNoFinishFragment noFinishFragment = new InventoryNoFinishFragment(assetBeans, pd_no);
        ft.replace(R.id.fl, noFinishFragment);
        ft.commitAllowingStateLoss();
    }

    private void getFinishedFragment(List<AssetBean> assetBeans) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        InventoryFinishedFragment finishedFragment = new InventoryFinishedFragment(assetBeans, pd_no);
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
                mResultList.get(i).setTag(pd_no + user_id + mResultList.get(i).getAsset_id());
            }
            assetBeanDao.deleteInTx(assetBeanDao.queryBuilder()
                    .where(assetBeanDao.queryBuilder()
                            .and(AssetBeanDao.Properties.Pd_no.eq(pd_no),
                                    AssetBeanDao.Properties.User_id.eq(user_id))).list());
            assetBeanDao.insertInTx(mResultList);
            if (pd_status == 4) {
                getFinishedFragment(mResultList);
            } else if (pd_status == 1 || pd_status == 3 || pd_status == 2) {
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
    public void syncAssetFailed(int type, String msg) {
        ToastUtils.toastMessage(this, msg);
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

    @Override
    public void keepSonAuditSuccess() {
        audit_asset_ids.clear();
        ToastUtils.toastMessage(this, "提交审核成功");
        pd_status = 2;
        initPdStatus();
        mPage = 1;
        mPresenter.showInventoryResult(pd_no, mPage);
    }

    private void methodRequiresCameraPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Intent intentScan = new Intent(InventoryResultActivity.this, ScanInventoryActivity.class);
            intentScan.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, pd_no);
            intentScan.putExtra("user_id", user_id);
            startActivityForResult(intentScan, Constant.REQUEST_CODE_INVENTORY_SCAN);
        } else {
            showCameraDialog();
        }
    }

    public void showCameraDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, false);
        builder.setTitle("提示");
        String content = "您暂未开启相机权限，请在设置" + "\n" + "中开启相机权限";
        builder.setText(content);
        builder.setUpDate(false);
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, "确定");
        builder.show();
    }

    @Override
    public void keepSonAuditFailed(int type, String msg) {
        ToastUtils.toastMessage(this, msg);
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
