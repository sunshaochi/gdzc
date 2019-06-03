package com.gengcon.android.fixedassets.module.inventory.view.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.iscandemo.ScannerInerface;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.ResultAsset;
import com.gengcon.android.fixedassets.module.base.BasePullRefreshActivity;
import com.gengcon.android.fixedassets.module.base.GApplication;
import com.gengcon.android.fixedassets.module.greendao.AssetBean;
import com.gengcon.android.fixedassets.module.greendao.AssetBeanDao;
import com.gengcon.android.fixedassets.module.greendao.InventoryBean;
import com.gengcon.android.fixedassets.module.greendao.InventoryBeanDao;
import com.gengcon.android.fixedassets.module.inventory.presenter.InventoryResultPresenter;
import com.gengcon.android.fixedassets.module.inventory.view.InventoryResultView;
import com.gengcon.android.fixedassets.rfid.BackResult;
import com.gengcon.android.fixedassets.rfid.GetRFIDThread;
import com.gengcon.android.fixedassets.rfid.MUtil;
import com.gengcon.android.fixedassets.rfid.RfidDialog;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.Logger;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.StringIsDigitUtil;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.widget.InfraredDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import realid.rfidlib.EmshConstant;

public class RFIDInventoryResultActivity extends BasePullRefreshActivity implements View.OnClickListener, InventoryResultView, BackResult {
    private GetRFIDThread rfidThread;
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private RfidDialog dialog;
    private InfraredDialog infraredDialog;
    private EmshStatusBroadcastReceiver mEmshStatusReceiver;
    private int currentStatue = -1;
    private boolean isConnect;

    private List<AssetBean> mResultList;
    private String pd_no;
    private String pd_name;
    private int pd_status;
    private int mPage = 1;

    private TextView tv_title_text, tv_title_status, tv_title_right;
    private LinearLayout noFinishLayout;
    private LinearLayout statusLayout;

    private InventoryResultPresenter mPresenter;

    private AssetBeanDao assetBeanDao;
    private InventoryBeanDao inventoryBeanDao;
    private List<AssetBean> assets;
    private List<String> asset_ids;
    private List<String> audit_asset_ids;
    private String user_id;
    private int isUpdate;
    private Handler mHandler;

    private ScannerInerface mControll;
    private SoundPool mSoundPool;
    private TextView pdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid_inventory);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
        rfidThread = new GetRFIDThread("MyHandlerThread");
        rfidThread.start();
        mHandler = new Handler(rfidThread.getLooper());//使用HandlerThread的looper对象创建Handler，如果使用默认的构造方法，很有可能阻塞UI线程
        mHandler.post(mBackgroundRunnable);
        monitorEmsh();
        rfidThread.setBackResult(this);

        mControll = new ScannerInerface(this);
        mControll.setOutputMode(1);
        registerReceiver(mScanReceiver, new IntentFilter("android.intent.action.SCANRESULT"));

        mSoundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        mSoundPool.load(this, R.raw.beep51, 1);
        initView();
    }

    private void monitorEmsh() {
        if (pd_status == 1 || pd_status == 3) {//进行中 驳回
            mEmshStatusReceiver = new EmshStatusBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter(EmshConstant.Action.INTENT_EMSH_BROADCAST);
            registerReceiver(mEmshStatusReceiver, intentFilter);

            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(EmshConstant.Action.INTENT_EMSH_REQUEST);
                    intent.putExtra(EmshConstant.IntentExtra.EXTRA_COMMAND, EmshConstant.Command.CMD_REFRESH_EMSH_STATUS);
                    sendBroadcast(intent);
                }
            };
            mTimer.schedule(mTimerTask, 0, 1000);
        }
    }

    //实现耗时操作的线程
    Runnable mBackgroundRunnable = new Runnable() {

        @Override
        public void run() {
            while (rfidThread.getFlag()) {
                if (rfidThread.isIfPostMsg()) {
                    String[] tagData = GApplication.getInstance().getIdataLib().readTagFromBuffer();
                    if (tagData != null) {
                        postResult(tagData[1]);
                        StringBuilder rssiStr = new StringBuilder((short) Integer.parseInt(tagData[2], 16) + "");
                        String rssi = rssiStr.insert(rssiStr.length() - 1, ".").toString();
                        Logger.e("rfid盘点", "tid_user = " + tagData[0] + " epc = " + tagData[1] + " rssi = " + rssi);
                    }
                }
            }

        }
    };

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        pdView = findViewById(R.id.pdView);
//        pdView.setTextColor(Color.parseColor("#666666"));
        pdView.setTextColor(getResources().getColor(R.color.gray_no));
        pdView.setEnabled(false);
        tv_title_text = findViewById(R.id.tv_title_text);
        tv_title_status = findViewById(R.id.tv_title_status);
        tv_title_right = findViewById(R.id.tv_title_right);
        noFinishLayout = findViewById(R.id.noFinishLayout);
        statusLayout = findViewById(R.id.statusLayout);
        tv_title_text.setText(pd_name);
        tv_title_right.setOnClickListener(this);
        findViewById(R.id.syncDataView).setOnClickListener(this);
        findViewById(R.id.auditView).setOnClickListener(this);
        findViewById(R.id.pdView).setOnClickListener(this);
        initPdStatus();
        if (assets.size() == 0) {
            if (!isNetworkConnected(this)) {
                if (pd_status == 4) {
                    getFinishedFragment();
                } else {
                    getNoFinishFragment();
                }
            } else {
                mPresenter.showInventoryResult(pd_no, mPage);
            }
        } else {
            if (pd_status == 4) {
                if (isUpdate != 1) {
                    if (!isNetworkConnected(this)) {
                        getFinishedFragment();
                    } else {
                        mPresenter.showInventoryResult(pd_no, mPage);
                    }
                } else {
                    getFinishedFragment();
                }
            } else if (pd_status == 2) {
                if (isUpdate != 1) {
                    if (!isNetworkConnected(this)) {
                        getNoFinishFragment();
                    } else {
                        mPresenter.showInventoryResult(pd_no, mPage);
                    }
                } else {
                    getNoFinishFragment();
                }
            } else {
                getNoFinishFragment();
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
        } else if (pd_status == 2) {
            tv_title_status.setText(R.string.inventory_wait);
            statusLayout.setBackgroundResource(R.drawable.bg_inventory_wait);
            tv_title_right.setVisibility(View.GONE);
            noFinishLayout.setVisibility(View.GONE);
        } else {
            tv_title_status.setText(R.string.inventory_finished);
            statusLayout.setBackgroundResource(R.drawable.bg_inventory_finished);
            tv_title_right.setVisibility(View.GONE);
            noFinishLayout.setVisibility(View.GONE);
        }
    }

    private void getNoFinishFragment() {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        InventoryNoFinishFragment noFinishFragment = InventoryNoFinishFragment.newInstance(user_id, pd_no);
        ft.replace(R.id.fl, noFinishFragment);
        ft.commitAllowingStateLoss();
    }

    private void getFinishedFragment() {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        InventoryFinishedFragment finishedFragment = InventoryFinishedFragment.newInstance(user_id, pd_no);
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
                getFinishedFragment();
            } else if (pd_status == 1 || pd_status == 3 || pd_status == 2) {
                getNoFinishFragment();
            }
        }
    }

    BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String scanResult = intent.getStringExtra("value");
            String result = scanResult.replaceAll(" ", "");
            if (result.startsWith("\\000026")) {
                result = result.substring(7);
            }
            if (dialog != null) {
                if (dialog.isShowing()) {
                    ToastUtils.toastMessage(RFIDInventoryResultActivity.this, "取消RFID盘点后才可进行红外盘点");
                    return;
                }
            }
            if (!TextUtils.isEmpty(result)) {
                if (pd_status == 1 || pd_status == 3) {
                    if (StringIsDigitUtil.isLetterDigit(result)) {
                        if (result.length() == 24) {
                            AssetBean asset = assetBeanDao.queryBuilder()
                                    .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                                    .where(AssetBeanDao.Properties.User_id.eq(user_id))
                                    .where(AssetBeanDao.Properties.Asset_id.eq(result))
                                    .unique();
                            if (asset != null) {
                                if (asset.getPd_status() == 1) {
                                    asset.setPd_status(2);
                                    asset.setIsScanAsset(1);
                                    assetBeanDao.update(asset);
                                    ToastUtils.toastMessage(RFIDInventoryResultActivity.this, "盘点成功");
                                    showInfraredDialog();
                                } else {
                                    ToastUtils.toastMessage(RFIDInventoryResultActivity.this, "该资产已盘点");
                                }
                            } else {
                                ToastUtils.toastMessage(RFIDInventoryResultActivity.this, "未查询到此资产");
                            }
                        } else {
                            ToastUtils.toastMessage(RFIDInventoryResultActivity.this, "非精臣固定资产有效二维码");
                        }
                    } else {
                        ToastUtils.toastMessage(RFIDInventoryResultActivity.this, "非精臣固定资产有效二维码");
                    }
                } else {
                    ToastUtils.toastMessage(RFIDInventoryResultActivity.this, "该盘点单已提交");
                }
            }
        }

    };

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

    public class EmshStatusBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            isConnect = false;
            pdView.setTextColor(getResources().getColor(R.color.gray_no));
            pdView.setEnabled(false);

            if (EmshConstant.Action.INTENT_EMSH_BROADCAST.equalsIgnoreCase(intent.getAction())) {
                int sessionStatus = intent.getIntExtra("SessionStatus", 0);
                int batteryPowerMode = intent.getIntExtra("BatteryPowerMode", -1);
                if ((sessionStatus & EmshConstant.EmshSessionStatus.EMSH_STATUS_POWER_STATUS) != 0) {
                    isConnect = true;
                    pdView.setTextColor(Color.parseColor("#333333"));
                    pdView.setEnabled(true);
                    // 把枪电池当前状态
                    if (batteryPowerMode == currentStatue) { //相同状态不处理
                        return;
                    }
                    currentStatue = batteryPowerMode;
                    switch (batteryPowerMode) {
                        case EmshConstant.EmshBatteryPowerMode.EMSH_PWR_MODE_STANDBY://0
                            GApplication.getInstance().getIdataLib().powerOn();
                            break;
                        case EmshConstant.EmshBatteryPowerMode.EMSH_PWR_MODE_DSG_UHF://2
                            GApplication.getInstance().getIdataLib().powerSet(30);
//                            MUtil.show("上电成功..."+"功率=="+GApplication.getInstance().getIdataLib().powerGet());
                            MUtil.show("上电成功...");
                            break;
                    }
                } else {
                    //未上电的action="android.intent.extra.EMSH_STATUS" sessionStatus = 0 batteryPowerMode  = 0
                    stopRFID();
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                        dialog = null;
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                startRFID();
                break;
        }
    }

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

    /**
     * 开始
     */
    private void startRFID() {
        boolean ifPostMsg = rfidThread.isIfPostMsg();
        if (!ifPostMsg) {//没扫描
            GApplication.getInstance().getIdataLib().startInventoryTag();
            showRfidInventoryingDialog();
            rfidThread.setIfPostMsg(true);
        }
    }

    /**
     * 结束
     */
    private void stopRFID() {
        boolean ifPostMsg = rfidThread.isIfPostMsg();
        if (ifPostMsg) {//再扫描
            GApplication.getInstance().getIdataLib().stopInventory();
            rfidThread.setIfPostMsg(false);
        }

    }

    private void showRfidInventoryingDialog() {
        if (dialog == null) {
            long noFinishAssetCount = assetBeanDao.queryBuilder()
                    .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                    .where(AssetBeanDao.Properties.User_id.eq(user_id))
                    .where(AssetBeanDao.Properties.Pd_status.eq(1)).count();
            dialog = new RfidDialog(RFIDInventoryResultActivity.this).builder();
            dialog.setTotal((int) noFinishAssetCount);
            dialog.setStopClick(new RfidDialog.StopListener() {
                @Override
                public void onClick() {
                    if (dialog.getLeft().equals("暂停")) {
                        stopRFID();
                        dialog.setLeft("开始");

                    } else {
                        startRFID();
                        dialog.setLeft("暂停");
                    }
                }
            });

            dialog.setQuxiaoClick(new RfidDialog.QuxiaoListener() {
                @Override
                public void onClick() {
                    stopRFID();
//                    realDataMap.clear();
//                    realKeyList.clear();
                    dialog.dismiss();
//                    dialog = null;
                }
            });

            dialog.setCompleteClick(new RfidDialog.CompileListener() {
                @Override
                public void onClick() {
                    stopRFID();
                    dialog.dismiss();
                    new MyAsyncTask().execute();
                }
            });
        }
        dialog.setLeft("暂停");
        dialog.setNum(realKeyList.size() + "");
        dialog.show();
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
            infraredDialog = new InfraredDialog(RFIDInventoryResultActivity.this).builder();
        }
        infraredDialog.setWpNum((int) noFinishAssetCount);
        infraredDialog.setYpNum(finishAssetCount + "");
        infraredDialog.setManualClick(new InfraredDialog.ManualListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(RFIDInventoryResultActivity.this, SearchAssetActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, pd_no);
                startActivityForResult(intent, Constant.REQUEST_CODE_INVENTORY_MANUAL);
            }
        });

        infraredDialog.setCompleteClick(new InfraredDialog.CompleteListener() {
            @Override
            public void onClick() {
                getNoFinishFragment();
                infraredDialog.dismiss();
                infraredDialog = null;
            }
        });
        if (!infraredDialog.isShowing()) {
            infraredDialog.show();
        }
    }

    private long rNumber = 0;//读取次数
    private Map<String, Integer> dataMap = new HashMap<>(); //数据
    private Map<String, Integer> realDataMap = new HashMap<>(); //数据
    private List<String> realKeyList = new ArrayList<>(); //存key

    @Override
    public void postResult(String epc) {
        ++rNumber;
        Integer number = dataMap.get(epc);//如果已存在，就拿到数量
        if (number == null) {
            dataMap.put(epc, 1);
            updateUI(epc, 1);
        } else {
            int newNB = number + 1;
            dataMap.put(epc, ++newNB);
            updateUI(epc, newNB);
        }
    }

    /**
     * 更新ui并把数据统计下来
     *
     * @param epc
     * @param readNumberss
     */

    private void updateUI(final String epc, final int readNumberss) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (readNumberss > 1) {
                    realDataMap.put(epc, readNumberss); //修改数量
                } else {
                    realDataMap.put(epc, 1);
                    realKeyList.add(epc);
                }
//                useTimes.setText(takeTime + usTim); //花费的时间
                if (realKeyList != null && realKeyList.size() > 0) {
                    dialog.setNum(realKeyList.size() + "");
                } else {
                    dialog.setNum("0");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
//        recyclerResources();
    }

    private void recyclerResources() {
        if (mEmshStatusReceiver != null) {
            unregisterReceiver(mEmshStatusReceiver);
            mEmshStatusReceiver = null;
        }
        if (mTimer != null || mTimerTask != null) {
            mTimerTask.cancel();
            mTimer.cancel();
            mTimerTask = null;
            mTimer = null;
        }
//        MUtil.cancelWaringDialog();
        rfidThread.destoryThread();
        Logger.e("powoff = ", "" + GApplication.getInstance().getIdataLib().powerOff());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constant.PREVIEW_CODE && resultCode == RESULT_OK) {
            onBackPressed();
        } else if (requestCode == Constant.REQUEST_CODE_INVENTORY_MANUAL && resultCode == Constant.RESULT_OK_INVENTORY_MANUAL) {
            showInfraredDialog();
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
        recyclerResources();//释放rfid相关对象
        mHandler.removeCallbacks(mBackgroundRunnable);//销毁线程
        mPresenter.detachView();
    }

    private class MyAsyncTask extends AsyncTask<String, Void, List<AssetBean>> {

        @Override
        protected List<AssetBean> doInBackground(String... strings) {
            if (realKeyList.size() > 0) {
                for (int i = 0; i < realKeyList.size(); i++) {
                    AssetBean asset = assetBeanDao.queryBuilder()
                            .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                            .where(AssetBeanDao.Properties.User_id.eq(user_id))
                            .where(AssetBeanDao.Properties.Asset_id.eq(realKeyList.get(i).toLowerCase()))
                            .unique();
                    if (asset != null) {
                        if (asset.getPd_status() != 2) {
                            asset.setPd_status(2);
                            asset.setIsScanAsset(1);
                            assetBeanDao.update(asset);
                        }
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected void onPostExecute(List<AssetBean> assetBeans) {
            super.onPostExecute(assetBeans);
            hideLoading();
            getNoFinishFragment();
            dataMap.clear();
            realDataMap.clear();
            realKeyList.clear();
        }
    }
}
