package com.gengcon.android.fixedassets.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iscandemo.ScannerInerface;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.adapter.AssetAdapter;
import com.gengcon.android.fixedassets.adapter.OnItemClickListener;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.AssetBean;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InventoryDetail;
import com.gengcon.android.fixedassets.bean.result.InventoryHeadDetail;
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.presenter.InventoryDetailPresenter;
import com.gengcon.android.fixedassets.presenter.UploadInventoryPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.RFIDUtils;
import com.gengcon.android.fixedassets.util.RolePowerManager;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.InventoryDetailView;
import com.gengcon.android.fixedassets.view.UploadInventoryView;
import com.gengcon.android.fixedassets.widget.AlertDialog;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.tbruyelle.rxpermissions2.Permission;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class InventoryDetailsActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener, InventoryDetailView, UploadInventoryView {

    private final long INVENTORY_DELAY = 10;
    private RefreshLayout mRefreshLayout;
    private MyRecyclerView mRecyclerView;
    private AssetAdapter mAdapter;
    private TextView mTvSize, mTvRemarks, mTvUser, mTvInventoryName, mTvNotInventotySize;
    private InventoryDetailPresenter mDetailPresenter;
    private UploadInventoryPresenter mUploadPresenter;
    private int mPage = 1;
    private String mInventoryId;
    private List<AssetBean> mResultInventoryList;
    private InventoryDetail inventoryDetail;
    private ArrayList<String> mReadAssetsIds;
    private int mInventorySize = 0;
    private boolean mIsOpenRFID = false;
    private Runnable mInventoryRunnable;
    private Handler mHandler;
    private AlertDialog.Builder mRfidBuilder;
    private ScannerInerface mControll;
    private SoundPool mSoundPool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);
        initView();

        mInventoryId = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID);
        if (mInventoryId == null) {
            finish();
            return;
        }
        mReadAssetsIds = new ArrayList<>();
        mResultInventoryList = new ArrayList<>();
        mDetailPresenter = new InventoryDetailPresenter();
        mUploadPresenter = new UploadInventoryPresenter();
        mDetailPresenter.attachView(this);
        mUploadPresenter.attachView(this);
        mDetailPresenter.getInventoryDetail(mInventoryId, mPage);
        mDetailPresenter.getInventoryHead(mInventoryId);
//        mRolePresenter.getRole("pd_page");

        mControll = new ScannerInerface(this);
        mControll.setOutputMode(1);
        registerReceiver(mScanReceiver, new IntentFilter("android.intent.action.SCANRESULT"));

        mSoundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool.load(this, R.raw.beep51, 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mControll.open();
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.inventory_details);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((ImageView) findViewById(R.id.iv_title_right)).setImageResource(R.drawable.ic_rfid_disconnect);
        mTvSize = findViewById(R.id.tv_size);
        mTvRemarks = findViewById(R.id.tv_remarks);
        mTvUser = findViewById(R.id.tv_user);
        mTvInventoryName = findViewById(R.id.tv_inventory_name);
        mTvNotInventotySize = findViewById(R.id.tv_not_inventory_size);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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
                mDetailPresenter.getInventoryDetail(mInventoryId, ++mPage);
            }
        });

        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.iv_title_right).setOnClickListener(this);
        findViewById(R.id.btn_single_inventory).setOnClickListener(this);
        findViewById(R.id.btn_start_inventory).setOnClickListener(this);
        findViewById(R.id.btn_preview).setOnClickListener(this);
        findViewById(R.id.btn_upload).setOnClickListener(this);

        //TODO 单条盘点权限
        if (RolePowerManager.getInstance().check("")) {
            findViewById(R.id.ll_bottom).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_single_inventory).setVisibility(View.VISIBLE);
        }
        //TODO PDA盘点权限
        if (RolePowerManager.getInstance().check("")) {
            findViewById(R.id.ll_bottom).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_start_inventory).setVisibility(View.VISIBLE);
        }
        if (RolePowerManager.getInstance().check("inventory/preview")) {
            findViewById(R.id.ll_bottom).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_preview).setVisibility(View.VISIBLE);
        }
        if (RolePowerManager.getInstance().check("inventory/uploadResult")) {
            findViewById(R.id.ll_bottom).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_upload).setVisibility(View.VISIBLE);
        }
        mHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsOpenRFID = RFIDUtils.isConnect();
        ((ImageView) findViewById(R.id.iv_title_right)).setImageResource(mIsOpenRFID ? R.drawable.ic_rfid_connect : R.drawable.ic_rfid_disconnect);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            List<String> ids = (ArrayList<String>) data.getSerializableExtra(Constant.INTENT_EXTRA_KEY_QR_SCAN);
            for (int i = 0; i < ids.size(); i++) {
                addAssetId(ids.get(i));
            }
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
        mDetailPresenter.detachView();
        mUploadPresenter.detachView();
        mHandler.removeCallbacks(mInventoryRunnable);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.iv_title_right:
                if (mIsOpenRFID) {
                    RFIDUtils.disconnect((GApplication) getApplication());
                    mIsOpenRFID = false;
                    ((ImageView) findViewById(R.id.iv_title_right)).setImageResource(R.drawable.ic_rfid_disconnect);
                    ToastUtils.toastMessage(InventoryDetailsActivity.this, "断开成功");
                } else {
                    boolean isConnect = RFIDUtils.connect((GApplication) getApplication());
                    mIsOpenRFID = isConnect;
                    ((ImageView) findViewById(R.id.iv_title_right)).setImageResource(isConnect ? R.drawable.ic_rfid_connect : R.drawable.ic_rfid_disconnect);
                    ToastUtils.toastMessage(this, isConnect ? getString(R.string.connect) + getString(R.string.success) : getString(R.string.connect) + getString(R.string.fail));
                }
//                Intent intent = new Intent(this, RFIDConnectActivity.class);
//                startActivity(intent);
                break;
            case R.id.btn_single_inventory:
                requestPermission(mCamearConsumer, Manifest.permission.CAMERA);
                break;
            case R.id.btn_start_inventory:
                if (!mIsOpenRFID) {
                    showRfidErrorDialog();
                } else {
                    showRfidInventoryingDialog();
                    readRFID();
                }
                break;
            case R.id.btn_preview:
                if (mReadAssetsIds.size() > 0) {
                    Intent intentPreview = new Intent(this, PreviewActivity.class);
                    intentPreview.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, mInventoryId);
                    intentPreview.putExtra(Constant.INTENT_EXTRA_KEY_ASSET_IDS, mReadAssetsIds);
                    startActivity(intentPreview);
                } else {
                    ToastUtils.toastMessage(this, R.string.plase_inventory);
                }
                break;
            case R.id.btn_upload:
                if (mReadAssetsIds.size() > 0) {
                    showUploadDialog();
                } else {
                    ToastUtils.toastMessage(this, R.string.plase_inventory);
                }
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.BEDETAIL);
        intent.putExtra("webName", "资产详情");
        intent.putExtra(Constant.INTENT_EXTRA_KEY_ASSER_ID, mResultInventoryList.get(position).getAsset_id());
        startActivity(intent);
    }

    private Consumer<Permission> mCamearConsumer = new Consumer<Permission>() {
        @Override
        public void accept(Permission permission) {
            if (permission.granted) {
                Intent intent = new Intent(InventoryDetailsActivity.this, ScanActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, mInventoryId);
                intent.putExtra(Constant.INTENT_EXTRA_KEY_ASSET_IDS, mReadAssetsIds);
                startActivityForResult(intent, Constant.REQ_QR_CODE);
            } else if (permission.shouldShowRequestPermissionRationale) {
                ToastUtils.toastMessage(InventoryDetailsActivity.this, R.string.permission_camera_tips);
                requestPermission(this, Manifest.permission.CAMERA);
            } else {
                ToastUtils.toastMessage(InventoryDetailsActivity.this, R.string.permission_camera_tips);
            }
        }
    };

    private Observer<Boolean> mRfidStatus = new Observer<Boolean>() {
        Disposable disposable;

        @Override
        public void onSubscribe(Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(Boolean aBoolean) {
            mIsOpenRFID = aBoolean;
            ((ImageView) findViewById(R.id.iv_title_right)).setImageResource(mIsOpenRFID ? R.drawable.ic_rfid_connect : R.drawable.ic_rfid_disconnect);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            disposable.dispose();
        }
    };

    private void readRFID() {
        if (mInventoryRunnable == null) {
            mInventoryRunnable = new Runnable() {
                @Override
                public void run() {
                    String result = RFIDUtils.startInventory((GApplication) getApplication());
                    if (!result.equals(RFIDUtils.CONNECT_FAILED)
                            && !result.equals(RFIDUtils.INVENTORY_FAILED)
                            && !result.equals(RFIDUtils.FAILED_TO_READ_INFORMATION)) {
                        if (addAssetId(result)) {
                            Log.e("Inventory", "run: " + result);
                            mSoundPool.play(1, 1, 1, 0, 0, 1);
                        }
                        mRfidBuilder.changeText(getString(R.string.pending_inventory_assets) + mResultInventoryList.size() + getString(R.string.item) + "\r\n" + getString(R.string.actual_inventory_size) + mReadAssetsIds.size() + getString(R.string.item));
                    }
                    mHandler.postDelayed(mInventoryRunnable, INVENTORY_DELAY);
                }
            };
        }
        mHandler.postDelayed(mInventoryRunnable, INVENTORY_DELAY);
    }

    BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String scanResult = intent.getStringExtra("value");
            if (!TextUtils.isEmpty(scanResult)) {
                ToastUtils.toastMessage(InventoryDetailsActivity.this, R.string.infra_red_scan_tips);
                addAssetId(scanResult);
            }
        }

    };

    private boolean addAssetId(String id) {
        if (duplicate(id)) {
            mReadAssetsIds.add(id);
            checkId(id);
            return true;
            /*SpannableString spannableString = new SpannableString(getString(R.string.pending_inventory_assets) + (mResultInventoryDetail.getAsset_ids().size() - mInventorySize) + getString(R.string.item));
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray_text)), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 5, spannableString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray_text)), spannableString.length() - 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvNotInventotySize.setText(spannableString);*/
        }
        return false;
    }

    private void checkId(String id) {
        for (String assetId : inventoryDetail.getAll_ids()) {
            if (id.equals(assetId)) {
                mInventorySize++;
            }
        }
    }

    private boolean duplicate(String id) {
        for (String assetId : mReadAssetsIds) {
            if (id.equals(assetId)) {
                return false;
            }
        }
        return true;
    }

    private void showRfidInventoryingDialog() {
        mRfidBuilder = new AlertDialog.Builder(this);
        mRfidBuilder.setTitle(R.string.inventorying);
        mRfidBuilder.setText(getString(R.string.pending_inventory_assets) + inventoryDetail.getAll_ids().size() + getString(R.string.item) + "\r\n" + getString(R.string.actual_inventory_size) + mReadAssetsIds.size() + getString(R.string.item));
        mRfidBuilder.setNegativeDismiss(false);
        mRfidBuilder.setNeutralButtonClickable(false);
        mRfidBuilder.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mRfidBuilder.getNegativeText().equals(getString(R.string.pause))) {
                    mHandler.removeCallbacks(mInventoryRunnable);
                    mRfidBuilder.changeNegativeText(getString(R.string.continu));
                    mRfidBuilder.setNeutralButtonClickable(true);
                } else {
                    readRFID();
                    mRfidBuilder.changeNegativeText(getString(R.string.pause));
                    mRfidBuilder.setNeutralButtonClickable(false);
                }
            }
        }, getString(R.string.pause));
        mRfidBuilder.setNeutralButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mRfidBuilder.getNegativeText().equals(getString(R.string.continu))) {
//                    if (RolePowerManager.getInstance().checkInventoryPower(getString(R.string.preview))) {
                        Intent intent = new Intent(InventoryDetailsActivity.this, PreviewActivity.class);
                        intent.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, mInventoryId);
                        intent.putExtra(Constant.INTENT_EXTRA_KEY_ASSET_IDS, mReadAssetsIds);
                        startActivity(intent);
//                    } else {
//                        showPermissionDeniedTips();
//                    }
                }
            }
        }, getString(R.string.preview));
        mRfidBuilder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mHandler.removeCallbacks(mInventoryRunnable);
            }
        }, getString(R.string.complete));
        mRfidBuilder.show();
    }

    private void showRfidErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tips);
        builder.setText(getString(R.string.open_rfid) + "\r\n" + getString(R.string.if_using_ordinary_labels) + "\r\n" + getString(R.string.place_click_single_inventory));
//        builder.setNegativeButton(new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        }, getString(R.string.cancel));
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, getString(R.string.confirm));
        builder.show();
    }

    private void showUploadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tips);
        SpannableString spannableString = new SpannableString(getString(R.string.pending_inventory_assets) + mResultInventoryList.size() + getString(R.string.item) + "\r\n"
                + getString(R.string.actual_inventory_size) + mReadAssetsIds.size() + getString(R.string.item) + "\r\n"
                + getString(R.string.can_upload));
        builder.setText(spannableString.toString());
        builder.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, getString(R.string.cancel));
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mUploadPresenter.uploadInventoryResult(mInventoryId, mReadAssetsIds);
            }
        }, getString(R.string.confirm));
        builder.show();
    }

    @Override
    public void initDetail(InventoryDetail inventoryDetailResult) {
        mRefreshLayout.finishLoadmore();
        inventoryDetail = inventoryDetailResult;
       /* SpannableString spannableString = new SpannableString(getString(R.string.pending_inventory_assets) + (resultInventoryDetail.getAssetList().getTotal_count() - mInventorySize) + getString(R.string.item));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray_text)), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 5, spannableString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray_text)), spannableString.length() - 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvNotInventotySize.setText(spannableString);*/
        mTvNotInventotySize.setText(getString(R.string.pending_inventory_assets) + "");

        SpannableString spannableString = new SpannableString(getString(R.string.total) + inventoryDetailResult.getTotal() + getString(R.string.item));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_text)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 1, spannableString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_text)), spannableString.length() - 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvSize.setText(spannableString);
        mAdapter.addDataSource(inventoryDetailResult.getList());
        mResultInventoryList.addAll(inventoryDetailResult.getList());
        if (mPage >= inventoryDetailResult.getPage_count()) {
            mRefreshLayout.setEnableLoadmore(false);
        } else {
            mRefreshLayout.setEnableLoadmore(true);
        }
    }

    @Override
    public void initHeadDetail(InventoryHeadDetail headDetail) {
        mTvInventoryName.setText(headDetail.getInv_name());
        mTvUser.setText(headDetail.getAllot_username());
        mTvRemarks.setText(headDetail.getRemark());
    }

    @Override
    public void uploadResult(Bean resultBean) {
        ToastUtils.toastMessage(this, resultBean.getMsg());
        Intent intent = new Intent(this, InventoryListActivity.class);
        startActivity(intent);
        finish();
    }
}
