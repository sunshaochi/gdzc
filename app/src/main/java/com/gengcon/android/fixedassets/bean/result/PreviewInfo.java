package com.gengcon.android.fixedassets.bean.result;

import com.gengcon.android.fixedassets.bean.AssetBean;

import java.io.Serializable;
import java.util.List;

public class PreviewInfo extends InvalidBean implements Serializable {
    /**
     * base_data : {"normal_num":1,"surplus_num":0,"loss_num":13,"invalid_num":0}
     * asset_data : {"total":1,"page_size":10,"page_count":1,"current_page":1,"list":[{"asset_code":"JC2JHLA4LY","asset_id":"0mtls5nuhssm4saj20rhdt8p4b","photourl":"","status":1,"asset_name":"2121212","created_at":"2018-11-28 10:39:47","custom_type_name":"通用","org_name":"总经办","emp_name":"弯弯","user_name":""}]}
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
         * normal_num : 1
         * surplus_num : 0
         * loss_num : 13
         * invalid_num : 0
         */

        private int normal_num;
        private int surplus_num;
        private int loss_num;
        private int invalid_num;

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

        public int getInvalid_num() {
            return invalid_num;
        }

        public void setInvalid_num(int invalid_num) {
            this.invalid_num = invalid_num;
        }
    }

    public static class AssetDataBean {
        /**
         * total : 1
         * page_size : 10
         * page_count : 1
         * current_page : 1
         * list : [{"asset_code":"JC2JHLA4LY","asset_id":"0mtls5nuhssm4saj20rhdt8p4b","photourl":"","status":1,"asset_name":"2121212","created_at":"2018-11-28 10:39:47","custom_type_name":"通用","org_name":"总经办","emp_name":"弯弯","user_name":""}]
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
