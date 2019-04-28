package com.gengcon.android.fixedassets.bean.result;


import java.io.Serializable;
import java.util.List;

public class AddAssetList extends InvalidBean implements Serializable {


    /**
     * default_tplid : pq2ini0b5fp8e338v4fcismpfj
     * list : [{"tpl_id":"default","tpl_name":"系统资产通用模板","scene":1},{"tpl_id":"2drcq49np78cda7p8huiucqkh3","tpl_name":"资产通用模板","scene":3},{"tpl_id":"pq2ini0b5fp8e338v4fcismpfj","tpl_name":"测试ding","scene":3}]
     * header_part : [{"attr_id":"asset_code","attr_name":"资产编码","attr_type":1,"default_data":"","is_required":1,"is_show":1},{"attr_id":"asset_name","attr_name":"资产名称","attr_type":1,"default_data":"","is_required":1,"is_show":1},{"attr_id":"custom_type_id","attr_name":"资产分类","attr_type":4,"default_data":"","is_required":1,"is_show":1,"bind_api":"common/getCustomType"},{"attr_id":"org_id","attr_name":"所属组织","attr_type":4,"default_data":"","is_required":1,"is_show":1,"bind_api":"common/getOrg"},{"attr_id":"admin_userid","attr_name":"管理员","attr_type":4,"default_data":"","is_required":0,"is_show":1,"bind_api":"common/getUser"},{"attr_id":"use_org_id","attr_name":"使用组织","attr_type":4,"default_data":"","is_required":0,"is_show":1,"bind_api":"common/getOrg"},{"attr_id":"emp_id","attr_name":"使用人","attr_type":4,"default_data":"","is_required":0,"is_show":1,"bind_api":"common/getEmp"},{"attr_id":"area_id","attr_name":"区域","attr_type":4,"default_data":"","is_required":0,"is_show":1,"bind_api":"common/getArea"},{"attr_id":"storage_position","attr_name":"存放地点","attr_type":1,"default_data":"","is_required":0,"is_show":1}]
     * footer_part : [{"attr_id":"standard","attr_name":"规格型号","attr_type":1,"default_data":"","is_required":0,"is_show":1},{"attr_id":"unit","attr_name":"计量单位","attr_type":1,"default_data":"","is_required":0,"is_show":1},{"attr_id":"worth","attr_name":"价值(元)","attr_type":1,"default_data":"","is_required":0,"is_show":1},{"attr_id":"time_limit","attr_name":"使用期限(月)","attr_type":1,"default_data":"","is_required":0,"is_show":1},{"attr_id":"buy_at","attr_name":"购买日期","attr_type":5,"default_data":"","is_required":0,"is_show":1},{"attr_id":"supplier","attr_name":"供应商","attr_type":1,"default_data":"","is_required":0,"is_show":1},{"attr_id":"remarks","attr_name":"备注","attr_type":2,"default_data":"","is_required":0,"is_show":1},{"attr_id":"photourl","attr_name":"照片","attr_type":3,"default_data":"","is_required":0,"is_show":1}]
     */

    private String default_tplid;
    private String default_tplname;
    private List<ListBean> list;
    private List<AddAsset> header_part;
    private List<AddAsset> footer_part;

    public String getDefault_tplid() {
        return default_tplid;
    }

    public void setDefault_tplid(String default_tplid) {
        this.default_tplid = default_tplid;
    }

    public String getDefault_tplname() {
        return default_tplname;
    }

    public void setDefault_tplname(String default_tplname) {
        this.default_tplname = default_tplname;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<AddAsset> getHeader_part() {
        return header_part;
    }

    public void setHeader_part(List<AddAsset> header_part) {
        this.header_part = header_part;
    }

    public List<AddAsset> getFooter_part() {
        return footer_part;
    }

    public void setFooter_part(List<AddAsset> footer_part) {
        this.footer_part = footer_part;
    }

    public static class ListBean {
        /**
         * tpl_id : default
         * tpl_name : 系统资产通用模板
         * scene : 1
         */

        private String tpl_id;
        private String tpl_name;
        private int scene;

        public String getTpl_id() {
            return tpl_id;
        }

        public void setTpl_id(String tpl_id) {
            this.tpl_id = tpl_id;
        }

        public String getTpl_name() {
            return tpl_name;
        }

        public void setTpl_name(String tpl_name) {
            this.tpl_name = tpl_name;
        }

        public int getScene() {
            return scene;
        }

        public void setScene(int scene) {
            this.scene = scene;
        }
    }

    public static class HeaderPartBean {
        /**
         * attr_id : asset_code
         * attr_name : 资产编码
         * attr_type : 1
         * default_data :
         * is_required : 1
         * is_show : 1
         * bind_api : common/getCustomType
         */

        private String attr_id;
        private String attr_name;
        private int attr_type;
        private String default_data;
        private int is_required;
        private int is_show;
        private String bind_api;

        public String getAttr_id() {
            return attr_id;
        }

        public void setAttr_id(String attr_id) {
            this.attr_id = attr_id;
        }

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }

        public int getAttr_type() {
            return attr_type;
        }

        public void setAttr_type(int attr_type) {
            this.attr_type = attr_type;
        }

        public String getDefault_data() {
            return default_data;
        }

        public void setDefault_data(String default_data) {
            this.default_data = default_data;
        }

        public int getIs_required() {
            return is_required;
        }

        public void setIs_required(int is_required) {
            this.is_required = is_required;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public String getBind_api() {
            return bind_api;
        }

        public void setBind_api(String bind_api) {
            this.bind_api = bind_api;
        }
    }

    public static class FooterPartBean {
        /**
         * attr_id : standard
         * attr_name : 规格型号
         * attr_type : 1
         * default_data :
         * is_required : 0
         * is_show : 1
         */

        private String attr_id;
        private String attr_name;
        private int attr_type;
        private String default_data;
        private int is_required;
        private int is_show;

        public String getAttr_id() {
            return attr_id;
        }

        public void setAttr_id(String attr_id) {
            this.attr_id = attr_id;
        }

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }

        public int getAttr_type() {
            return attr_type;
        }

        public void setAttr_type(int attr_type) {
            this.attr_type = attr_type;
        }

        public String getDefault_data() {
            return default_data;
        }

        public void setDefault_data(String default_data) {
            this.default_data = default_data;
        }

        public int getIs_required() {
            return is_required;
        }

        public void setIs_required(int is_required) {
            this.is_required = is_required;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }
    }
}
