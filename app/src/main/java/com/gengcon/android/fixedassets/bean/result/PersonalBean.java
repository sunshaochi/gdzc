package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class PersonalBean extends InvalidBean implements Serializable {


    /**
     * user_name : sendy
     * phone : 15717118197
     * role_name : 超级管理员
     * org_name : 蓝翔
     * industry_name : 建筑业
     * version_id : 4
     * grade_id : 2
     * version_name : 高级版
     * grade_name : VIP-2000
     * asset_max_num : 2000
     */

    private String user_name;
    private String phone;
    private String role_name;
    private String org_name;
    private String industry_name;
    private int version_id;
    private int grade_id;
    private String version_name;
    private String grade_name;
    private String due_at;
    private int asset_max_num;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getIndustry_name() {
        return industry_name;
    }

    public void setIndustry_name(String industry_name) {
        this.industry_name = industry_name;
    }

    public int getVersion_id() {
        return version_id;
    }

    public void setVersion_id(int version_id) {
        this.version_id = version_id;
    }

    public int getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(int grade_id) {
        this.grade_id = grade_id;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getDue_at() {
        return due_at;
    }

    public void setDue_at(String due_at) {
        this.due_at = due_at;
    }

    public int getAsset_max_num() {
        return asset_max_num;
    }

    public void setAsset_max_num(int asset_max_num) {
        this.asset_max_num = asset_max_num;
    }
}
