package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class ResultRole implements Serializable {


    /**
     * asset_module : {"cn":"资产入库","is_show":true,"type":"module"}
     * asset_change : {"cn":"资产-变更","is_show":true,"type":"action"}
     * asset_recipients : {"cn":"资产-领用","is_show":true,"type":"action"}
     * asset_refund : {"cn":"资产-退还","is_show":true,"type":"action"}
     * asset_lend : {"cn":"资产-借用","is_show":true,"type":"action"}
     * asset_returned : {"cn":"资产-归还","is_show":true,"type":"action"}
     * asset_transfer : {"cn":"资产-调拨","is_show":true,"type":"action"}
     * asset_repair : {"cn":"资产-维修","is_show":true,"type":"action"}
     * asset_scrap : {"cn":"资产-报废","is_show":true,"type":"action"}
     * asset_repair_finish : {"cn":"资产-维修完成","is_show":true,"type":"action"}
     * asset_edit : {"cn":"资产-编辑","is_show":true,"type":"action"}
     * asset_remarks : {"cn":"资产-维修完成","is_show":true,"type":"action"}
     * asset_del : {"cn":"资产-删除","is_show":true,"type":"action"}
     * audit_module : {"cn":"审批模块","is_show":true,"type":"module"}
     * emp_module : {"cn":"员工（模块）","is_show":true,"type":"module"}
     * org_module : {"cn":"组织（模块）","is_show":true,"type":"module"}
     */

    private RoleBean asset_module;
    private RoleBean asset_change;
    private RoleBean asset_recipients;
    private RoleBean asset_refund;
    private RoleBean asset_lend;
    private RoleBean asset_returned;
    private RoleBean asset_transfer;
    private RoleBean asset_repair;
    private RoleBean asset_scrap;
    private RoleBean asset_repair_finish;
    private RoleBean asset_edit;
    private RoleBean asset_remarks;
    private RoleBean asset_del;
    private RoleBean audit_module;
    private RoleBean emp_module;
    private RoleBean org_module;

    public RoleBean getAsset_module() {
        return asset_module;
    }

    public void setAsset_module(RoleBean asset_module) {
        this.asset_module = asset_module;
    }

    public RoleBean getAsset_change() {
        return asset_change;
    }

    public void setAsset_change(RoleBean asset_change) {
        this.asset_change = asset_change;
    }

    public RoleBean getAsset_recipients() {
        return asset_recipients;
    }

    public void setAsset_recipients(RoleBean asset_recipients) {
        this.asset_recipients = asset_recipients;
    }

    public RoleBean getAsset_refund() {
        return asset_refund;
    }

    public void setAsset_refund(RoleBean asset_refund) {
        this.asset_refund = asset_refund;
    }

    public RoleBean getAsset_lend() {
        return asset_lend;
    }

    public void setAsset_lend(RoleBean asset_lend) {
        this.asset_lend = asset_lend;
    }

    public RoleBean getAsset_returned() {
        return asset_returned;
    }

    public void setAsset_returned(RoleBean asset_returned) {
        this.asset_returned = asset_returned;
    }

    public RoleBean getAsset_transfer() {
        return asset_transfer;
    }

    public void setAsset_transfer(RoleBean asset_transfer) {
        this.asset_transfer = asset_transfer;
    }

    public RoleBean getAsset_repair() {
        return asset_repair;
    }

    public void setAsset_repair(RoleBean asset_repair) {
        this.asset_repair = asset_repair;
    }

    public RoleBean getAsset_scrap() {
        return asset_scrap;
    }

    public void setAsset_scrap(RoleBean asset_scrap) {
        this.asset_scrap = asset_scrap;
    }

    public RoleBean getAsset_repair_finish() {
        return asset_repair_finish;
    }

    public void setAsset_repair_finish(RoleBean asset_repair_finish) {
        this.asset_repair_finish = asset_repair_finish;
    }

    public RoleBean getAsset_edit() {
        return asset_edit;
    }

    public void setAsset_edit(RoleBean asset_edit) {
        this.asset_edit = asset_edit;
    }

    public RoleBean getAsset_remarks() {
        return asset_remarks;
    }

    public void setAsset_remarks(RoleBean asset_remarks) {
        this.asset_remarks = asset_remarks;
    }

    public RoleBean getAsset_del() {
        return asset_del;
    }

    public void setAsset_del(RoleBean asset_del) {
        this.asset_del = asset_del;
    }

    public RoleBean getAudit_module() {
        return audit_module;
    }

    public void setAudit_module(RoleBean audit_module) {
        this.audit_module = audit_module;
    }

    public RoleBean getEmp_module() {
        return emp_module;
    }

    public void setEmp_module(RoleBean emp_module) {
        this.emp_module = emp_module;
    }

    public RoleBean getOrg_module() {
        return org_module;
    }

    public void setOrg_module(RoleBean org_module) {
        this.org_module = org_module;
    }

    public static class RoleBean {
        /**
         * cn : 资产-打印
         * allow : true
         * type : action
         */

        private String cn;
        private boolean is_show;
        private String type;

        public String getCn() {
            return cn;
        }

        public void setCn(String cn) {
            this.cn = cn;
        }

        public boolean isAllow() {
            return is_show;
        }

        public void setAllow(boolean allow) {
            this.is_show = allow;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
