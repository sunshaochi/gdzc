package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class Approval implements Serializable {


    public static final int WAIT = 1;
    public static final int AGREE = 2;
    public static final int REJECT = 3;


    /**
     * doc_no : DB201903211041495125
     * created_username : wxh
     * doc_type : 3
     * created_at : 2019-03-21
     * auditor_at :
     * auditor_name :
     * status : 1
     * asset_num : 2
     * doc_type_cn : 资产调拨
     * status_cn : 待审批
     */

    private String doc_no;
    private String created_username;
    private int doc_type;
    private String created_at;
    private String auditor_at;
    private String auditor_name;
    private int status;
    private int asset_num;
    private String doc_type_cn;
    private String status_cn;

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getCreated_username() {
        return created_username;
    }

    public void setCreated_username(String created_username) {
        this.created_username = created_username;
    }

    public int getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(int doc_type) {
        this.doc_type = doc_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAuditor_at() {
        return auditor_at;
    }

    public void setAuditor_at(String auditor_at) {
        this.auditor_at = auditor_at;
    }

    public String getAuditor_name() {
        return auditor_name;
    }

    public void setAuditor_name(String auditor_name) {
        this.auditor_name = auditor_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAsset_num() {
        return asset_num;
    }

    public void setAsset_num(int asset_num) {
        this.asset_num = asset_num;
    }

    public String getDoc_type_cn() {
        return doc_type_cn;
    }

    public void setDoc_type_cn(String doc_type_cn) {
        this.doc_type_cn = doc_type_cn;
    }

    public String getStatus_cn() {
        return status_cn;
    }

    public void setStatus_cn(String status_cn) {
        this.status_cn = status_cn;
    }
}
