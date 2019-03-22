package com.gengcon.android.fixedassets.bean.result;


import java.io.Serializable;

public class ApprovalNum extends InvalidBean implements Serializable {


    /**
     * pending_num : 1000
     * processed_num : 100
     */

    private int pending_num;
    private int processed_num;

    public int getPending_num() {
        return pending_num;
    }

    public void setPending_num(int pending_num) {
        this.pending_num = pending_num;
    }

    public int getProcessed_num() {
        return processed_num;
    }

    public void setProcessed_num(int processed_num) {
        this.processed_num = processed_num;
    }
}
