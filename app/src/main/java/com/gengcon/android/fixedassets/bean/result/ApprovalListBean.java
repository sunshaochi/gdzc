package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;
import java.util.List;

public class ApprovalListBean extends InvalidBean implements Serializable {
    /**
     * total : 5
     * page_size : 15
     * page_count : 1
     * current_page : 1
     * list : [{"id":38758,"doc_no":"BF201903041658497651","doc_type":6,"root_org_id":21006,"created_username":"sendy","created_userid":5462,"created_at":"2019-03-04","doc_remarks":"","status":1,"reason":"","auditor_id":0,"auditor_at":0,"auditor_name":"","doc_type_cn":"报废"},{"id":38754,"doc_no":"DB201903041648175459","doc_type":3,"root_org_id":21006,"created_username":"sendy","created_userid":5462,"created_at":"2019-03-04","doc_remarks":"1大概","status":1,"reason":"","auditor_id":0,"auditor_at":0,"auditor_name":"","doc_type_cn":"调拨"},{"id":38752,"doc_no":"DB201903041640085633","doc_type":3,"root_org_id":21006,"created_username":"sendy","created_userid":5462,"created_at":"2019-03-04","doc_remarks":"","status":1,"reason":"","auditor_id":0,"auditor_at":0,"auditor_name":"","doc_type_cn":"调拨"},{"id":38751,"doc_no":"DB201903041637364758","doc_type":3,"root_org_id":21006,"created_username":"sendy","created_userid":5462,"created_at":"2019-03-04","doc_remarks":"","status":1,"reason":"","auditor_id":0,"auditor_at":0,"auditor_name":"","doc_type_cn":"调拨"},{"id":38746,"doc_no":"DB201902280942579555","doc_type":3,"root_org_id":21006,"created_username":"sendy","created_userid":5462,"created_at":"2019-02-28","doc_remarks":"北大法宝出现波动性风险白癜风","status":1,"reason":"","auditor_id":0,"auditor_at":0,"auditor_name":"","doc_type_cn":"调拨"}]
     */

    private int total;
    private int page_size;
    private int page_count;
    private int current_page;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 38758
         * doc_no : BF201903041658497651
         * doc_type : 6
         * root_org_id : 21006
         * created_username : sendy
         * created_userid : 5462
         * created_at : 2019-03-04
         * doc_remarks :
         * status : 1
         * reason :
         * auditor_id : 0
         * auditor_at : 0
         * auditor_name :
         * doc_type_cn : 报废
         */

        private int id;
        private String doc_no;
        private int doc_type;
        private int root_org_id;
        private String created_username;
        private int created_userid;
        private String created_at;
        private String doc_remarks;
        private int status;
        private String reason;
        private int auditor_id;
        private int auditor_at;
        private String auditor_name;
        private String doc_type_cn;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDoc_no() {
            return doc_no;
        }

        public void setDoc_no(String doc_no) {
            this.doc_no = doc_no;
        }

        public int getDoc_type() {
            return doc_type;
        }

        public void setDoc_type(int doc_type) {
            this.doc_type = doc_type;
        }

        public int getRoot_org_id() {
            return root_org_id;
        }

        public void setRoot_org_id(int root_org_id) {
            this.root_org_id = root_org_id;
        }

        public String getCreated_username() {
            return created_username;
        }

        public void setCreated_username(String created_username) {
            this.created_username = created_username;
        }

        public int getCreated_userid() {
            return created_userid;
        }

        public void setCreated_userid(int created_userid) {
            this.created_userid = created_userid;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getDoc_remarks() {
            return doc_remarks;
        }

        public void setDoc_remarks(String doc_remarks) {
            this.doc_remarks = doc_remarks;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getAuditor_id() {
            return auditor_id;
        }

        public void setAuditor_id(int auditor_id) {
            this.auditor_id = auditor_id;
        }

        public int getAuditor_at() {
            return auditor_at;
        }

        public void setAuditor_at(int auditor_at) {
            this.auditor_at = auditor_at;
        }

        public String getAuditor_name() {
            return auditor_name;
        }

        public void setAuditor_name(String auditor_name) {
            this.auditor_name = auditor_name;
        }

        public String getDoc_type_cn() {
            return doc_type_cn;
        }

        public void setDoc_type_cn(String doc_type_cn) {
            this.doc_type_cn = doc_type_cn;
        }
    }
}
