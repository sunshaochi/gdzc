package com.gengcon.android.fixedassets.module.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class InventoryBean {


    /**
     * pd_no : PD201905051452143914
     * pd_name : 资产数量限制
     * status : 1
     * audit_userid : 3759
     * created_userid : 3759
     * created_at : 2019-05-05 14:52:14
     * wp_num : 23
     * yp_num : 0
     * py_num : 0
     * asset_updated_at : 2019-05-05 14:52:14
     * created_username : wxh
     * audit_username : wxh
     * status_cn : 进行中
     */

    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String tag;
    private String pd_no;
    private String pd_name;
    private Integer status;
    private Integer audit_userid;
    private Integer created_userid;
    private String created_at;
    private Integer wp_num;
    private Integer yp_num;
    private Integer py_num;
    private String asset_updated_at;
    private String created_username;
    private String audit_username;
    private String status_cn;
    private Integer son_status;
    private String user_id;

    @Generated(hash = 1550479186)
    public InventoryBean(Long id, String tag, String pd_no, String pd_name, Integer status, Integer audit_userid,
            Integer created_userid, String created_at, Integer wp_num, Integer yp_num, Integer py_num,
            String asset_updated_at, String created_username, String audit_username, String status_cn, Integer son_status,
            String user_id) {
        this.id = id;
        this.tag = tag;
        this.pd_no = pd_no;
        this.pd_name = pd_name;
        this.status = status;
        this.audit_userid = audit_userid;
        this.created_userid = created_userid;
        this.created_at = created_at;
        this.wp_num = wp_num;
        this.yp_num = yp_num;
        this.py_num = py_num;
        this.asset_updated_at = asset_updated_at;
        this.created_username = created_username;
        this.audit_username = audit_username;
        this.status_cn = status_cn;
        this.son_status = son_status;
        this.user_id = user_id;
    }

    @Generated(hash = 1976819531)
    public InventoryBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPd_no() {
        return this.pd_no;
    }

    public void setPd_no(String pd_no) {
        this.pd_no = pd_no;
    }

    public String getPd_name() {
        return this.pd_name;
    }

    public void setPd_name(String pd_name) {
        this.pd_name = pd_name;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAudit_userid() {
        return this.audit_userid;
    }

    public void setAudit_userid(Integer audit_userid) {
        this.audit_userid = audit_userid;
    }

    public Integer getCreated_userid() {
        return this.created_userid;
    }

    public void setCreated_userid(Integer created_userid) {
        this.created_userid = created_userid;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getWp_num() {
        return this.wp_num;
    }

    public void setWp_num(Integer wp_num) {
        this.wp_num = wp_num;
    }

    public Integer getYp_num() {
        return this.yp_num;
    }

    public void setYp_num(Integer yp_num) {
        this.yp_num = yp_num;
    }

    public Integer getPy_num() {
        return this.py_num;
    }

    public void setPy_num(Integer py_num) {
        this.py_num = py_num;
    }

    public String getAsset_updated_at() {
        return this.asset_updated_at;
    }

    public void setAsset_updated_at(String asset_updated_at) {
        this.asset_updated_at = asset_updated_at;
    }

    public String getCreated_username() {
        return this.created_username;
    }

    public void setCreated_username(String created_username) {
        this.created_username = created_username;
    }

    public String getAudit_username() {
        return this.audit_username;
    }

    public void setAudit_username(String audit_username) {
        this.audit_username = audit_username;
    }

    public String getStatus_cn() {
        return this.status_cn;
    }

    public void setStatus_cn(String status_cn) {
        this.status_cn = status_cn;
    }

    public Integer getSon_status() {
        return son_status;
    }

    public void setSon_status(Integer son_status) {
        this.son_status = son_status;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
