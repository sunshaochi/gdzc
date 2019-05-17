package com.gengcon.android.fixedassets.module.inventory.view.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.base.BasePullRefreshActivity;
import com.gengcon.android.fixedassets.module.base.GApplication;
import com.gengcon.android.fixedassets.module.inventory.view.RfidInventoryView;
import com.gengcon.android.fixedassets.rfid.BackResult;
import com.gengcon.android.fixedassets.rfid.GetRFIDThread;
import com.gengcon.android.fixedassets.rfid.MUtil;
import com.gengcon.android.fixedassets.rfid.RfidDialog;
import com.gengcon.android.fixedassets.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.trinea.android.common.util.ToastUtils;
import realid.rfidlib.EmshConstant;

public class RfidInventoryResultActivity extends BasePullRefreshActivity implements View.OnClickListener, RfidInventoryView, BackResult {
    private TextView tv_inventory;
    private GetRFIDThread rfidThread;
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private RfidDialog dialog;
    private EmshStatusBroadcastReceiver mEmshStatusReceiver;
    private int currentStatue = -1;
    private boolean isconnet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_rfidinverntor);
        initView();
        rfidThread = GetRFIDThread.getInstance();
        new Thread(rfidThread).start();
        monitorEmsh();
        GetRFIDThread.getInstance().setBackResult(this);
    }

    private void monitorEmsh() {
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

    @Override
    protected void initView() {
        super.initView();
        tv_inventory = findViewById(R.id.tv_inventory);
        tv_inventory.setOnClickListener(this);
    }

    public class EmshStatusBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (EmshConstant.Action.INTENT_EMSH_BROADCAST.equalsIgnoreCase(intent.getAction())) {
                int sessionStatus = intent.getIntExtra("SessionStatus", 0);
                int batteryPowerMode = intent.getIntExtra("BatteryPowerMode", -1);
//                Logger.e("监控把枪状态","action= "+intent.getAction()+" sessionStatus = " + sessionStatus + "  batteryPowerMode  = " + batteryPowerMode);
//                ToastUtils.toastMessage(InventoryAct.this,"监控把枪状态"+" action= "+intent.getAction()+" sessionStatus = " + sessionStatus + "  batteryPowerMode  = " + batteryPowerMode+"");
                if ((sessionStatus & EmshConstant.EmshSessionStatus.EMSH_STATUS_POWER_STATUS) != 0) {
                    isconnet = true;
                    // 把枪电池当前状态
                    if (batteryPowerMode == currentStatue) { //相同状态不处理
                        return;
                    }
                    currentStatue = batteryPowerMode;
                    switch (batteryPowerMode) {
                        case EmshConstant.EmshBatteryPowerMode.EMSH_PWR_MODE_STANDBY://0
//                            MUtil.cancelWaringDialog();
                            GApplication.getInstance().getIdataLib().powerOn();
//                                GApplication.getInstance().getIdataLib().powerSet(30);
                            break;
                        case EmshConstant.EmshBatteryPowerMode.EMSH_PWR_MODE_DSG_UHF://2
                            MUtil.show("上电成功...");
                            break;
                    }
                } else {//未上电的action="android.intent.extra.EMSH_STATUS" sessionStatus = 0 batteryPowerMode  = 0
                    isconnet = false;
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
            case R.id.tv_inventory:
                if (isconnet) {
                    startRFID();
                } else {
                    ToastUtils.show(RfidInventoryResultActivity.this, "电源无法开启");
                }

                break;
        }

    }

    /**
     * 开始
     */
    private void startRFID() {
        boolean flag = GetRFIDThread.getInstance().isIfPostMsg();
        if (!flag) {//没扫描
            GApplication.getInstance().getIdataLib().startInventoryTag();
            showRfidInventoryingDialog();
            GetRFIDThread.getInstance().setIfPostMsg(true);
            com.gengcon.android.fixedassets.util.ToastUtils.toastMessage(RfidInventoryResultActivity.this, "开始扫描");
        }

    }

    /**
     * 结束
     */
    private void stopRFID() {
        boolean flag = GetRFIDThread.getInstance().isIfPostMsg();
        if (flag) {//再扫描
            GApplication.getInstance().getIdataLib().stopInventory();
            GetRFIDThread.getInstance().setIfPostMsg(false);
            com.gengcon.android.fixedassets.util.ToastUtils.toastMessage(RfidInventoryResultActivity.this, "开始扫描");
        }

    }

    private void showRfidInventoryingDialog() {
        if (dialog == null) {
            dialog = new RfidDialog(RfidInventoryResultActivity.this).builder();
            dialog.setTotal(100);
            dialog.setNum(realDataMap.size() + "");
            dialog.setLeft("暂停");
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
                    realDataMap.clear();
                    realKeyList.clear();
                    dialog.dismiss();
                    dialog = null;
                }
            });

            dialog.setCompleteClick(new RfidDialog.CompileListener() {
                @Override
                public void onClick() {
                    stopRFID();
                    realDataMap.clear();
                    realKeyList.clear();
                    dialog.dismiss();
                    dialog = null;
                }
            });
        }

        dialog.show();
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
                dialog.setNum(realDataMap.size() + "");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerResources();
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
        Logger.e("powoff = ", "" + GApplication.getInstance().getIdataLib().powerOff());
    }

}
