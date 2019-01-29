package com.gengcon.android.fixedassets.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.example.iscandemo.ScannerInerface;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.Home;
import com.gengcon.android.fixedassets.bean.result.ResultRole;
import com.gengcon.android.fixedassets.bean.result.UserPopupNotice;
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.model.AssetDetailModel;
import com.gengcon.android.fixedassets.presenter.HomePresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.RFIDUtils;
import com.gengcon.android.fixedassets.util.RolePowerManager;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.StringIsDigitUtil;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.HomeView;
import com.gengcon.android.fixedassets.widget.AlertDialog;
import com.tbruyelle.rxpermissions2.Permission;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseActivity implements View.OnClickListener, HomeView {

    private TextView mTvSize;
    private TextView inUseSize;
    private TextView idleSize;
    private HomePresenter mPresenter;
    private boolean mIsBackPressed;
    private ScannerInerface mControll;
    private ImageView messageView;
    private UserPopupNotice userPopupNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mControll = new ScannerInerface(this);
        mControll.setOutputMode(1);

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        initReceiver();
        // 获取测试设备ID
        String testDeviceId = StatService.getTestDeviceId(this);
        // 日志输出
        android.util.Log.d("BaiduMobStat", "Test DeviceId : " + testDeviceId);
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.app_name);
        ((ImageView) findViewById(R.id.asset_management).findViewById(R.id.item_main_img)).setImageResource(R.drawable.ic_asset_management);
        ((TextView) findViewById(R.id.asset_management).findViewById(R.id.item_main_text)).setText(R.string.asset_management);
        ((ImageView) findViewById(R.id.asset_storage).findViewById(R.id.item_main_img)).setImageResource(R.drawable.ic_asset_storage);
        ((TextView) findViewById(R.id.asset_storage).findViewById(R.id.item_main_text)).setText(R.string.asset_storage);
        ((ImageView) findViewById(R.id.inventory_management).findViewById(R.id.item_main_img)).setImageResource(R.drawable.ic_inventory_management);
        ((TextView) findViewById(R.id.inventory_management).findViewById(R.id.item_main_text)).setText(R.string.inventory_management);
        ((ImageView) findViewById(R.id.processing_record).findViewById(R.id.item_main_img)).setImageResource(R.drawable.ic_processing_record);
        ((TextView) findViewById(R.id.processing_record).findViewById(R.id.item_main_text)).setText(R.string.processing_record);
        ((ImageView) findViewById(R.id.analysis_report).findViewById(R.id.item_main_img)).setImageResource(R.drawable.ic_analysis_report);
        ((TextView) findViewById(R.id.analysis_report).findViewById(R.id.item_main_text)).setText(R.string.analysis_report);
        ((ImageView) findViewById(R.id.device_management).findViewById(R.id.item_main_img)).setImageResource(R.drawable.ic_device_management);
        ((TextView) findViewById(R.id.device_management).findViewById(R.id.item_main_text)).setText(R.string.setting_management);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_user);
        ((ImageView) findViewById(R.id.iv_title_right)).setImageResource(R.drawable.ic_qr);
        ((ImageView) findViewById(R.id.iv_title_msg)).setImageResource(R.drawable.ic_msg);

        mTvSize = findViewById(R.id.tv_size);
        inUseSize = findViewById(R.id.asset_inUse);
        idleSize = findViewById(R.id.asset_idle);
        messageView = findViewById(R.id.iv_title_msg);

        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.iv_title_right).setOnClickListener(this);
        findViewById(R.id.asset_management).setOnClickListener(this);
        findViewById(R.id.asset_storage).setOnClickListener(this);
        findViewById(R.id.inventory_management).setOnClickListener(this);
        findViewById(R.id.processing_record).setOnClickListener(this);
        findViewById(R.id.analysis_report).setOnClickListener(this);
        findViewById(R.id.device_management).setOnClickListener(this);
        findViewById(R.id.totalLayout).setOnClickListener(this);
        findViewById(R.id.usingLayout).setOnClickListener(this);
        findViewById(R.id.freeLayout).setOnClickListener(this);
        messageView.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mControll.open();
        registerReceiver(mScanReceiver, new IntentFilter("android.intent.action.SCANRESULT"));
        if (TextUtils.isEmpty((CharSequence) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.TOKEN, ""))) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty((CharSequence) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.TOKEN, ""))) {
            mPresenter.getHome();
            mPresenter.getRoute();
            mPresenter.getUserNotice();
//            mRolePresenter.getRole("home_page");
        }
    }

    /**
     * 注册网络监听的广播
     */
    private void initReceiver() {
        IntentFilter timeFilter = new IntentFilter();
        timeFilter.addAction("android.net.ethernet.ETHERNET_STATE_CHANGED");
        timeFilter.addAction("android.net.ethernet.STATE_CHANGE");
        timeFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        timeFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        timeFilter.addAction("android.net.wifi.STATE_CHANGE");
        timeFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(netReceiver, timeFilter);
    }

    BroadcastReceiver netReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
                        Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    int type2 = networkInfo.getType();
                    String typeName = networkInfo.getTypeName();

                    switch (type2) {
                        case 0://移动 网络    2G 3G 4G 都是一样的 实测 mix2s 联通卡
                            mPresenter.getRoute();
                            mPresenter.getHome();
                            break;
                        case 1: //wifi网络
                            mPresenter.getRoute();
                            mPresenter.getHome();
                            break;
                    }
                }
            }
        }

    };

    @Override
    public void onBackPressed() {
        if (!mIsBackPressed) {
            mIsBackPressed = true;
            ToastUtils.toastMessage(this, R.string.click_exit_app);
        } else {
            super.onBackPressed();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIsBackPressed = false;
            }
        }, 2000);
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
    protected void onDestroy() {
        super.onDestroy();
        if (netReceiver != null) {
            unregisterReceiver(netReceiver);
            netReceiver = null;
        }
        mControll.close();
        RFIDUtils.disconnect((GApplication) getApplication());
        mPresenter.detachView();
    }

    @Override
    public void onClick(View view) {
        String msg = "网络连接不给力,请检查您的网络";
        Intent webIntent = new Intent(MainActivity.this, WebActivity.class);
        switch (view.getId()) {
            case R.id.iv_title_left:
                webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.USER);
                webIntent.putExtra("webName", "用户中心");
                webIntent.putExtra("webFrom", "MainActivity");
                startActivity(webIntent);
                break;
            case R.id.iv_title_right:
                if (isNetworkConnected(this)) {
                    requestPermission(mCamearConsumer, Manifest.permission.CAMERA);
                } else {
                    ToastUtils.toastMessage(this, msg);
                }
                return;
            case R.id.inventory_management:
                if (isNetworkConnected(this)) {
                    if (RolePowerManager.getInstance().isInventoryModule()) {
                        Intent intent = new Intent(MainActivity.this, InventoryListActivity.class);
                        startActivity(intent);
                    } else {
                        ToastUtils.toastMessage(this, "当前您没有权限");
                    }
                } else {
                    ToastUtils.toastMessage(this, msg);
                }
                return;
            case R.id.iv_title_msg:
                Intent intent = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(intent);
                return;
            case R.id.asset_management:
                if (isNetworkConnected(this)) {
                    if (RolePowerManager.getInstance().isAssetModule()) {
                        webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.ASSET_MANAGE + URL.ASSET_STATUS + "0");
                        webIntent.putExtra("webName", "资产列表");
                        webIntent.putExtra("webTitle", "选择");
                        webIntent.putExtra("webFrom", "MainActivity");
                        startActivity(webIntent);
                    } else {
                        ToastUtils.toastMessage(this, "当前您没有权限");
                    }
                } else {
                    ToastUtils.toastMessage(this, msg);
                }
                break;
            case R.id.asset_storage:
                if (isNetworkConnected(this)) {
                    if (RolePowerManager.getInstance().isAddModule()) {
                        webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.ASSET_STORAGE);
                        webIntent.putExtra("webName", "资产入库");
                        webIntent.putExtra("webTitle", "保存");
                        webIntent.putExtra("webFrom", "MainActivity");
                        startActivity(webIntent);
                    } else {
                        ToastUtils.toastMessage(this, "当前您没有权限");
                    }
                } else {
                    ToastUtils.toastMessage(this, msg);
                }
                break;
            case R.id.processing_record:
                if (isNetworkConnected(this)) {
                    if (RolePowerManager.getInstance().isDocModule()) {
                        webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.RECODE);
                        webIntent.putExtra("webName", "处理记录");
                        webIntent.putExtra("webFrom", "MainActivity");
                        startActivity(webIntent);
                    } else {
                        ToastUtils.toastMessage(this, "当前您没有权限");
                    }
                } else {
                    ToastUtils.toastMessage(this, msg);
                }
                break;
            case R.id.analysis_report:
                if (isNetworkConnected(this)) {
                    if (RolePowerManager.getInstance().isReportModule()) {
                        webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.ANALYSE);
                        webIntent.putExtra("webName", "分析报表");
                        webIntent.putExtra("webFrom", "MainActivity");
                        startActivity(webIntent);
                    } else {
                        ToastUtils.toastMessage(this, "当前您没有权限");
                    }
                } else {
                    ToastUtils.toastMessage(this, msg);
                }
                break;
            case R.id.device_management:
                if (isNetworkConnected(this)) {
                    if (RolePowerManager.getInstance().isEmpModule() || RolePowerManager.getInstance().isEmpModule()) {
                        webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.DEVICE_MANAGE);
                        webIntent.putExtra("webName", "设置管理");
                        webIntent.putExtra("webFrom", "MainActivity");
                        startActivity(webIntent);
                    } else {
                        ToastUtils.toastMessage(this, "当前您没有权限");
                    }
                } else {
                    ToastUtils.toastMessage(this, msg);
                }
                break;
            case R.id.totalLayout:
                if (isNetworkConnected(this)) {
                    if (RolePowerManager.getInstance().isAssetModule()) {
                        webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.ASSET_MANAGE + URL.ASSET_STATUS + "0");
                        webIntent.putExtra("webName", "资产列表");
                        webIntent.putExtra("webTitle", "选择");
                        webIntent.putExtra("webFrom", "MainActivity");
                        startActivity(webIntent);
                    } else {
                        ToastUtils.toastMessage(this, "当前您没有权限");
                    }
                } else {
                    ToastUtils.toastMessage(this, msg);
                }
                break;
            case R.id.freeLayout:
                if (isNetworkConnected(this)) {
                    if (RolePowerManager.getInstance().isAssetModule()) {
                        webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.ASSET_MANAGE + URL.ASSET_STATUS + "1");
                        webIntent.putExtra("webName", "资产列表");
                        webIntent.putExtra("webTitle", "选择");
                        webIntent.putExtra("webFrom", "MainActivity");
                        startActivity(webIntent);
                    } else {
                        ToastUtils.toastMessage(this, "当前您没有权限");
                    }
                } else {
                    ToastUtils.toastMessage(this, msg);
                }
                break;
            case R.id.usingLayout:
                if (isNetworkConnected(this)) {
                    if (RolePowerManager.getInstance().isAssetModule()) {
                        webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.ASSET_MANAGE + URL.ASSET_STATUS + "2");
                        webIntent.putExtra("webName", "资产列表");
                        webIntent.putExtra("webTitle", "选择");
                        webIntent.putExtra("webFrom", "MainActivity");
                        startActivity(webIntent);
                    } else {
                        ToastUtils.toastMessage(this, "当前您没有权限");
                    }
                } else {
                    ToastUtils.toastMessage(this, msg);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQ_QR_CODE && resultCode == Constant.RESULT_QR_CODE) {
            if (TextUtils.isEmpty(data.getStringExtra("resultString"))) {
                return;
            }
            final String assetId = data.getStringExtra("resultString");
            isAssetId(assetId);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mScanReceiver);
    }

    private Consumer<Permission> mCamearConsumer = new Consumer<Permission>() {
        @Override
        public void accept(Permission permission) throws Exception {
            if (permission.granted) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_KEY_SCAN_MODE, ScanActivity.QR_SCAN_LOGIN_MODE);
                startActivityForResult(intent, Constant.REQ_QR_CODE);
            } else if (permission.shouldShowRequestPermissionRationale) {
                ToastUtils.toastMessage(MainActivity.this, R.string.permission_camera_tips);
                requestPermission(this, Manifest.permission.CAMERA);
            } else {
                ToastUtils.toastMessage(MainActivity.this, R.string.permission_camera_tips);
            }
        }
    };

    private void isAssetId(final String assetId) {
        final AssetDetailModel mode = new AssetDetailModel();
        subscribe(mode.assetDetail(assetId),
                new ApiCallBack<Bean<Object>>() {
                    @Override
                    public void onSuccess(Bean<Object> modelBean) {
                        if (modelBean.getCode().equals("CODE_200")) {
                            if (modelBean.getData() != null) {
                                Intent webIntent = new Intent(MainActivity.this, WebActivity.class);
                                webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.SCANDETAIL + "?id=" + assetId);
                                webIntent.putExtra("webName", "资产详情");
                                startActivity(webIntent);
                            } else {
                                ToastUtils.toastMessage(MainActivity.this, "未找到该资产");
                            }
                        } else {
                            showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(int status, String errorMsg) {
                        showErrorMsg(status, errorMsg);
                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public void onStart() {

                    }
                });
    }

    BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String scanResult = intent.getStringExtra("value");
            if (!TextUtils.isEmpty(scanResult)) {
                addAssetId(scanResult);
            }
        }
    };

    private void addAssetId(String id) {
        if (id.startsWith("\\000026")) {
            id = id.substring(7);
        }
        if (StringIsDigitUtil.isLetterDigit(id)) {
            if (id.length() == 24) {
                isAssetId(id);
            } else {
                ToastUtils.toastMessage(this, "非固定资产二维码");
            }
        } else {
            ToastUtils.toastMessage(this, "非固定资产二维码");
        }
    }

    private void subscribe(Observable observable, Observer subscriber) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber);
    }

    @Override
    public void showHome(Home resultHome) {
        mTvSize.setText(resultHome.getTotal().getValue() + "");
        inUseSize.setText(resultHome.getUseing().getValue() + "");
        idleSize.setText(resultHome.getFree().getValue() + "");
    }

    @Override
    public void showApiRoute(ResultRole apiRoute) {
        RolePowerManager.getInstance().setApi_route(apiRoute);
    }

    @Override
    public void showNotice(UserPopupNotice userPopupNotice) {
        this.userPopupNotice = userPopupNotice;
        if (userPopupNotice.getUnread() == 0) {
            messageView.setImageResource(R.drawable.ic_msg);
        } else {
            messageView.setImageResource(R.drawable.ic_msg_unread);
        }
        if (userPopupNotice.getList() != null) {
            String photoUrl = userPopupNotice.getList().getPhotourl();
            int model = userPopupNotice.getList().getPush_model();
            if (!TextUtils.isEmpty(photoUrl)) {
                showNoticeImgDialog(photoUrl, model);
            } else {
                showNoticeDialog(model);
            }
        }
    }

    private void showNoticeImgDialog(String url, int model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, true);
        builder.setImg(url);
        if (model == 1) {
            builder.setPositiveButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }, "我知道了");
        } else {
            builder.setPositiveButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent webIntent = new Intent(MainActivity.this, MessageDetailsActivity.class);
                    webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.MESSAGEDETAIL + userPopupNotice.getList().getId());
                    startActivity(webIntent);
//                    mPresenter.getReadEditNotice(userPopupNotice.getList().getId());
                    dialog.dismiss();

                }
            }, "查看详情");
            builder.setNeutralButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    mPresenter.getReadEditNotice(userPopupNotice.getList().getId());
                    dialog.dismiss();
                }
            }, "我知道了");
        }
        builder.show();
    }

    private void showNoticeDialog(int model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, false);
        builder.setTitle(userPopupNotice.getList().getTitle());
        builder.setText(userPopupNotice.getList().getOutline());
        builder.setUpDate(true);
        if (model == 1) {
            builder.setPositiveButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }, "我知道了");
        } else {
            builder.setNeutralButtonColor();
            builder.setPositiveButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent webIntent = new Intent(MainActivity.this, MessageDetailsActivity.class);
                    webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.MESSAGEDETAIL + userPopupNotice.getList().getId());
                    startActivity(webIntent);
//                    mPresenter.getReadEditNotice(userPopupNotice.getList().getId());
                    dialog.dismiss();
                }
            }, "查看详情");
            builder.setNeutralButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    mPresenter.getReadEditNotice(userPopupNotice.getList().getId());
                    dialog.dismiss();
                }
            }, "我知道了");
        }
        builder.show();
    }
}
