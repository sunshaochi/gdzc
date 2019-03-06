package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class ApprovalHeadDetail implements Serializable {

    /**
     * cn : 调入组织
     * val : 人力资源部
     */

    private String cn;
    private String val;

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
