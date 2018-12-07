package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class InventoryHeadDetail implements Serializable {

    /**
     * inv_name : 仓库二期盘点计划
     * remark : 盘点计划备注内容
     * allot_userid : 3
     * is_delete : 0
     * allot_username : whzs
     */

    private String inv_name;
    private String remark;
    private int allot_userid;
    private int is_delete;
    private String allot_username;

    public String getInv_name() {
        return inv_name;
    }

    public void setInv_name(String inv_name) {
        this.inv_name = inv_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getAllot_userid() {
        return allot_userid;
    }

    public void setAllot_userid(int allot_userid) {
        this.allot_userid = allot_userid;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public String getAllot_username() {
        return allot_username;
    }

    public void setAllot_username(String allot_username) {
        this.allot_username = allot_username;
    }
}
