package com.gengcon.android.fixedassets.bean;

import java.io.Serializable;

public class Inventory implements Serializable {

    /**
     * 1.未盘, 2.正常, 3.异常
     */
    public static final int NOT_INVENTORY = 1;
    public static final int NORMAL = 2;
    public static final int ERROR = 3;

    String id;
    String created_at;
    String created_username;
    String allot_username;
    int status;
    String status_cn;
    String inv_name;
    String inv_no;
    int can_edit;

    public int getCan_edit() {
        return can_edit;
    }

    public void setCan_edit(int can_edit) {
        this.can_edit = can_edit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus_cn() {
        return status_cn;
    }

    public void setStatus_cn(String status_cn) {
        this.status_cn = status_cn;
    }

    public String getInv_name() {
        return inv_name;
    }

    public void setInv_name(String inv_name) {
        this.inv_name = inv_name;
    }

    public String getCreator_name() {
        return created_username;
    }

    public void setCreator_name(String created_username) {
        this.created_username = created_username;
    }

    public String getAllot_username() {
        return allot_username;
    }

    public void setAllot_username(String allot_username) {
        this.allot_username = allot_username;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getInv_no() {
        return inv_no;
    }

    public void setInv_no(String inv_no) {
        this.inv_no = inv_no;
    }
}
