package com.gengcon.android.fixedassets.bean.result;


import com.gengcon.android.fixedassets.bean.AssetBean;

import java.io.Serializable;
import java.util.List;

/**
 * 盘点返回結果
 */
public class InventoryR extends InvalidBean implements Serializable {

    /**
     * base_data : {"inv_name":"测试3","remark":"test","created_userid":31,"allot_username":"wanwan","normal_num":29,"surplus_num":1,"loss_num":0}
     * asset_data : {"total":30,"page_size":10,"page_count":3,"current_page":1,"list":[{"snap_id":48,"status":2,"result_status":4,"asset_name":"笔记本电脑","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"专用设备","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":47,"status":2,"result_status":4,"asset_name":"上下铺床","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"家具用具装具及动植物","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":46,"status":2,"result_status":4,"asset_name":"单人床","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"家具用具装具及动植物","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":45,"status":2,"result_status":4,"asset_name":"卧虎床垫","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"家具用具装具及动植物","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":44,"status":3,"result_status":4,"asset_name":"gggaaa","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"通讯设备","org_name":"弯弯的小公司","emp_name":"","user_name":"","status_cn":"报废"},{"snap_id":43,"status":2,"result_status":4,"asset_name":"文件柜","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"家具用具装具及动植物","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":42,"status":2,"result_status":4,"asset_name":"jklh","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"测试分类","org_name":"总经办","emp_name":"孙国梁","user_name":"","status_cn":"在用"},{"snap_id":41,"status":2,"result_status":4,"asset_name":"办公桌","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"专用设备","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":40,"status":2,"result_status":4,"asset_name":"上下铺床","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"家具用具装具及动植物","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":39,"status":2,"result_status":4,"asset_name":"卧虎床垫","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"家具用具装具及动植物","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"}]}
     */

    private BaseDataBean base_data;
    private AssetDataBean asset_data;

    public BaseDataBean getBase_data() {
        return base_data;
    }

    public void setBase_data(BaseDataBean base_data) {
        this.base_data = base_data;
    }

    public AssetDataBean getAsset_data() {
        return asset_data;
    }

    public void setAsset_data(AssetDataBean asset_data) {
        this.asset_data = asset_data;
    }

    public static class BaseDataBean {
        /**
         * inv_name : 测试3
         * remark : test
         * created_userid : 31
         * allot_username : wanwan
         * normal_num : 29
         * surplus_num : 1
         * loss_num : 0
         */

        private String inv_name;
        private String remark;
        private int created_userid;
        private String allot_username;
        private int normal_num;
        private int surplus_num;
        private int loss_num;

        public String getInv_name() {
            return inv_name;
        }

        public void setInv_name(String inv_name) {
            this.inv_name = inv_name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getCreated_userid() {
            return created_userid;
        }

        public void setCreated_userid(int created_userid) {
            this.created_userid = created_userid;
        }

        public String getAllot_username() {
            return allot_username;
        }

        public void setAllot_username(String allot_username) {
            this.allot_username = allot_username;
        }

        public int getNormal_num() {
            return normal_num;
        }

        public void setNormal_num(int normal_num) {
            this.normal_num = normal_num;
        }

        public int getSurplus_num() {
            return surplus_num;
        }

        public void setSurplus_num(int surplus_num) {
            this.surplus_num = surplus_num;
        }

        public int getLoss_num() {
            return loss_num;
        }

        public void setLoss_num(int loss_num) {
            this.loss_num = loss_num;
        }
    }

    public static class AssetDataBean {
        /**
         * total : 30
         * page_size : 10
         * page_count : 3
         * current_page : 1
         * list : [{"snap_id":48,"status":2,"result_status":4,"asset_name":"笔记本电脑","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"专用设备","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":47,"status":2,"result_status":4,"asset_name":"上下铺床","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"家具用具装具及动植物","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":46,"status":2,"result_status":4,"asset_name":"单人床","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"家具用具装具及动植物","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":45,"status":2,"result_status":4,"asset_name":"卧虎床垫","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"家具用具装具及动植物","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":44,"status":3,"result_status":4,"asset_name":"gggaaa","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"通讯设备","org_name":"弯弯的小公司","emp_name":"","user_name":"","status_cn":"报废"},{"snap_id":43,"status":2,"result_status":4,"asset_name":"文件柜","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"家具用具装具及动植物","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":42,"status":2,"result_status":4,"asset_name":"jklh","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"测试分类","org_name":"总经办","emp_name":"孙国梁","user_name":"","status_cn":"在用"},{"snap_id":41,"status":2,"result_status":4,"asset_name":"办公桌","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"专用设备","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":40,"status":2,"result_status":4,"asset_name":"上下铺床","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"家具用具装具及动植物","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"},{"snap_id":39,"status":2,"result_status":4,"asset_name":"卧虎床垫","inv_created_at":"2018-11-28 16:32:02","custom_type_name":"家具用具装具及动植物","org_name":"总经办","emp_name":"毛利超","user_name":"李静1","status_cn":"在用"}]
         */

        private int total;
        private int page_size;
        private int page_count;
        private int current_page;
        private List<AssetBean> list;

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

        public List<AssetBean> getList() {
            return list;
        }

        public void setList(List<AssetBean> list) {
            this.list = list;
        }
    }
}
