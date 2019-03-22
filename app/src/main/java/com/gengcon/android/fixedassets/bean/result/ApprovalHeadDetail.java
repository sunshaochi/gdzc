package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;
import java.util.List;

public class ApprovalHeadDetail implements Serializable {

    public static final int WAIT = 1;
    public static final int AGREE = 2;
    public static final int REJECT = 3;

    /**
     * baseinfo : [{"cn":"调入组织","val":"AA"},{"cn":"使用组织","val":""},{"cn":"使用人","val":""},{"cn":"调入日期","val":"2019-03-21"},{"cn":"备注","val":""}]
     * audit_info : {"doc_no":"DB201903211041495125","doc_type_cn":"资产调拨","created_username":"wxh","auditor_name":"","reason":"","created_at":"2019-03-21 10:41:49","auditor_at":"","status":1,"status_cn":"待审批"}
     */

    private AuditInfoBean audit_info;
    private List<BaseInfoBean> baseinfo;

    public AuditInfoBean getAudit_info() {
        return audit_info;
    }

    public void setAudit_info(AuditInfoBean audit_info) {
        this.audit_info = audit_info;
    }

    public List<BaseInfoBean> getBaseInfo() {
        return baseinfo;
    }

    public void setBaseInfo(List<BaseInfoBean> baseInfo) {
        this.baseinfo = baseInfo;
    }

    public static class AuditInfoBean {
        /**
         * doc_no : DB201903211041495125
         * doc_type_cn : 资产调拨
         * created_username : wxh
         * auditor_name :
         * reason :
         * created_at : 2019-03-21 10:41:49
         * auditor_at :
         * status : 1
         * status_cn : 待审批
         */

        private String doc_no;
        private String doc_type_cn;
        private String created_username;
        private String auditor_name;
        private String reason;
        private String created_at;
        private String auditor_at;
        private int status;
        private String status_cn;

        public String getDoc_no() {
            return doc_no;
        }

        public void setDoc_no(String doc_no) {
            this.doc_no = doc_no;
        }

        public String getDoc_type_cn() {
            return doc_type_cn;
        }

        public void setDoc_type_cn(String doc_type_cn) {
            this.doc_type_cn = doc_type_cn;
        }

        public String getCreated_username() {
            return created_username;
        }

        public void setCreated_username(String created_username) {
            this.created_username = created_username;
        }

        public String getAuditor_name() {
            return auditor_name;
        }

        public void setAuditor_name(String auditor_name) {
            this.auditor_name = auditor_name;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
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
    }

    public static class BaseInfoBean {
        /**
         * cn : 调入组织
         * val : AA
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
}
