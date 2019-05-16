package com.gengcon.android.fixedassets.bean;

import java.io.Serializable;

public class Asset implements Serializable {

    public static final int IDEL = 0x01; // 闲置
    public static final int IN_USE = 0x02;//使用中
    public static final int SCRAP = 0x03;//废弃
    public static final int PEPAIR = 0x04;//维修中
    public static final int LEND = 0x05;//借出
    public static final int SCRAP_AUDITING = 21;//报废审核中
    public static final int ALLOT_AUDITING = 22;//调拨审核中

    /**
     * 处理状态:0-未盘,2-正常,3-盘赢, 4-盘亏
     */
    public static final int INVENTORY_NOT = 0x01;
    public static final int INVENTORY_NORMAL = 0x02;
    public static final int INVENTORY_EARN = 0x03;
    public static final int INVENTORY_LOSS = 0x04;
    public static final int INVENTORY_ERROR = 0x04;


    /**
     * asset_code : JC243MQF14
     * asset_id : s4c6jvuoidcf5o7lgl16cmf4t6
     * photourl :
     * status : 1
     * asset_name : 哈哈哈哈
     * created_at : 2018-11-27 09:47:17
     * custom_type_name : 办公用品
     * org_name : 弯弯的小公司
     * emp_name :
     * user_name :
     * status_cn : 闲置
     */

    private String asset_code;
    private String asset_id;
    private String photourl;
    private int status;
    private String asset_name;
    private String created_at;
    private String custom_type_name;
    private String org_name;
    private String emp_name;
    private String user_name;
    private String status_cn;

    public String getAsset_code() {
        return asset_code;
    }

    public void setAsset_code(String asset_code) {
        this.asset_code = asset_code;
    }

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCustom_type_name() {
        return custom_type_name;
    }

    public void setCustom_type_name(String custom_type_name) {
        this.custom_type_name = custom_type_name;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getStatus_cn() {
        return status_cn;
    }

    public void setStatus_cn(String status_cn) {
        this.status_cn = status_cn;
    }
}
