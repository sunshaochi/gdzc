package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class ResultRole implements Serializable {
    /**
     * asset_module : {"cn":"资产管理模块","allow":true}
     * asset_print : {"cn":"资产-打印","allow":true,"type":"action"}
     * asset_change : {"cn":"资产-变更","allow":true,"type":"action"}
     * asset_recipients : {"cn":"资产-领用","allow":true,"type":"action"}
     * asset_lend : {"cn":"资产-借用","allow":true,"type":"action"}
     * asset_returned : {"cn":"资产-归还","allow":true,"type":"action"}
     * asset_transfer : {"cn":"资产-调拨","allow":true,"type":"action"}
     * asset_repair : {"cn":"资产-维修","allow":true,"type":"action"}
     * asset_edit : {"cn":"资产-编辑","allow":true,"type":"action"}
     * asset_del : {"cn":"资产-删除","allow":true,"type":"action"}
     * asset_add_module : {"cn":"资产添加模块","allow":true,"type":"module"}
     * inventory_module : {"cn":"资产盘点模块","allow":true,"type":"module"}
     * inventory_add : {"cn":"盘点-新建","allow":true,"type":"action"}
     * inventory_edit : {"cn":"盘点-编辑","allow":true,"type":"action"}
     * inventory_del : {"cn":"盘点-删除","allow":true,"type":"action"}
     * inventory_pd : {"cn":"盘点-盘点操作(上传盘点结果)","allow":true,"type":"action"}
     * doc_module : {"cn":"处理记录模块","allow":true,"type":"module"}
     * report_module : {"cn":"分析报表模块","allow":true,"type":"module"}
     * emp_module : {"cn":"员工模块","allow":true,"type":"module"}
     * emp_add : {"cn":"员工-添加","allow":true,"type":"action"}
     * emp_edit : {"cn":"员工-编辑","allow":true,"type":"action"}
     * emp_del : {"cn":"员工-删除","allow":true,"type":"action"}
     * org_module : {"cn":"组织模块","allow":true,"type":"module"}
     * org_add : {"cn":"组织-添加","allow":true,"type":"action"}
     * org_edit : {"cn":"组织-编辑","allow":true,"type":"action"}
     * org_del : {"cn":"组织-删除","allow":true,"type":"action"}
     */

    private RoleBean asset_module;
    private RoleBean asset_print;
    private RoleBean asset_change;
    private RoleBean asset_recipients;
    private RoleBean asset_lend;
    private RoleBean asset_returned;
    private RoleBean asset_transfer;
    private RoleBean asset_repair;
    private RoleBean asset_edit;
    private RoleBean asset_del;
    private RoleBean asset_add_module;
    private RoleBean inventory_module;
    private RoleBean inventory_add;
    private RoleBean inventory_edit;
    private RoleBean inventory_del;
    private RoleBean inventory_pd;
    private RoleBean doc_module;
    private RoleBean report_module;
    private RoleBean emp_module;
    private RoleBean emp_add;
    private RoleBean emp_edit;
    private RoleBean emp_del;
    private RoleBean org_module;
    private RoleBean org_add;
    private RoleBean org_edit;
    private RoleBean org_del;
    private RoleBean audit_module;

    public RoleBean getAsset_module() {
        return asset_module;
    }

    public void setAsset_module(RoleBean asset_module) {
        this.asset_module = asset_module;
    }

    public RoleBean getAsset_print() {
        return asset_print;
    }

    public void setAsset_print(RoleBean asset_print) {
        this.asset_print = asset_print;
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

    public RoleBean getAsset_edit() {
        return asset_edit;
    }

    public void setAsset_edit(RoleBean asset_edit) {
        this.asset_edit = asset_edit;
    }

    public RoleBean getAsset_del() {
        return asset_del;
    }

    public void setAsset_del(RoleBean asset_del) {
        this.asset_del = asset_del;
    }

    public RoleBean getAsset_add_module() {
        return asset_add_module;
    }

    public void setAsset_add_module(RoleBean asset_add_module) {
        this.asset_add_module = asset_add_module;
    }

    public RoleBean getInventory_module() {
        return inventory_module;
    }

    public void setInventory_module(RoleBean inventory_module) {
        this.inventory_module = inventory_module;
    }

    public RoleBean getInventory_add() {
        return inventory_add;
    }

    public void setInventory_add(RoleBean inventory_add) {
        this.inventory_add = inventory_add;
    }

    public RoleBean getInventory_edit() {
        return inventory_edit;
    }

    public void setInventory_edit(RoleBean inventory_edit) {
        this.inventory_edit = inventory_edit;
    }

    public RoleBean getInventory_del() {
        return inventory_del;
    }

    public void setInventory_del(RoleBean inventory_del) {
        this.inventory_del = inventory_del;
    }

    public RoleBean getInventory_pd() {
        return inventory_pd;
    }

    public void setInventory_pd(RoleBean inventory_pd) {
        this.inventory_pd = inventory_pd;
    }

    public RoleBean getDoc_module() {
        return doc_module;
    }

    public void setDoc_module(RoleBean doc_module) {
        this.doc_module = doc_module;
    }

    public RoleBean getReport_module() {
        return report_module;
    }

    public void setReport_module(RoleBean report_module) {
        this.report_module = report_module;
    }

    public RoleBean getEmp_module() {
        return emp_module;
    }

    public void setEmp_module(RoleBean emp_module) {
        this.emp_module = emp_module;
    }

    public RoleBean getEmp_add() {
        return emp_add;
    }

    public void setEmp_add(RoleBean emp_add) {
        this.emp_add = emp_add;
    }

    public RoleBean getEmp_edit() {
        return emp_edit;
    }

    public void setEmp_edit(RoleBean emp_edit) {
        this.emp_edit = emp_edit;
    }

    public RoleBean getEmp_del() {
        return emp_del;
    }

    public void setEmp_del(RoleBean emp_del) {
        this.emp_del = emp_del;
    }

    public RoleBean getOrg_module() {
        return org_module;
    }

    public void setOrg_module(RoleBean org_module) {
        this.org_module = org_module;
    }

    public RoleBean getOrg_add() {
        return org_add;
    }

    public void setOrg_add(RoleBean org_add) {
        this.org_add = org_add;
    }

    public RoleBean getOrg_edit() {
        return org_edit;
    }

    public void setOrg_edit(RoleBean org_edit) {
        this.org_edit = org_edit;
    }

    public RoleBean getOrg_del() {
        return org_del;
    }

    public void setOrg_del(RoleBean org_del) {
        this.org_del = org_del;
    }

    public RoleBean getAudit_module() {
        return audit_module;
    }

    public void setAudit_module(RoleBean audit_module) {
        this.audit_module = audit_module;
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
