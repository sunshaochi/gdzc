package com.gengcon.android.fixedassets.rfid;


import com.gengcon.android.fixedassets.module.base.GApplication;
import com.gengcon.android.fixedassets.util.Logger;

/**
 * author CYD
 * date 2018/11/19
 * email chengyd@idatachina.com
 */
public class GetRFIDThread implements Runnable {

    private GetRFIDThread() {
    }

    public static GetRFIDThread getInstance() {
        return MySingleton.instance;
    }

    static class MySingleton {
        static final GetRFIDThread instance = new GetRFIDThread();
    }

    private BackResult ba;

    private boolean ifPostMsg = false;

    public void setBackResult(BackResult ba) {
        this.ba = ba;
    }

    public boolean isIfPostMsg() {
        return ifPostMsg;
    }

    private boolean flag = true;

    public void destoryThread() {
        flag = false;
    }

    public void setIfPostMsg(boolean ifPostMsg) {
        this.ifPostMsg = ifPostMsg;
    }

    @Override
    public void run() {
        while (flag) {
            if (ifPostMsg) {
                String[] tagData = GApplication.getInstance().getIdataLib().readTagFromBuffer();
                if (tagData != null) {
                    ba.postResult(tagData[1]);
                    StringBuilder rssiStr = new StringBuilder((short) Integer.parseInt(tagData[2], 16) + "");
                    String rssi = rssiStr.insert(rssiStr.length() - 1, ".").toString();
                    Logger.e("rfid盘点","tid_user = " + tagData[0] + " epc = " + tagData[1] + " rssi = " + rssi);
                }
            }
        }
    }
}
