package com.gengcon.android.fixedassets.bean.result;

import com.gengcon.android.fixedassets.bean.AssetBean;

import java.io.Serializable;
import java.util.List;

public class InventoryDetail extends InvalidBean implements Serializable {


    /**
     * total : 3
     * page_size : 15
     * page_count : 1
     * current_page : 1
     * list : [{"asset_code":"JC243MQF14","asset_id":"s4c6jvuoidcf5o7lgl16cmf4t6","photourl":"","status":1,"asset_name":"哈哈哈哈","created_at":"2018-11-27 09:47:17","custom_type_name":"办公用品","org_name":"弯弯的小公司","emp_name":"","user_name":"","status_cn":"闲置"},{"asset_code":"JCW0CSEFCY","asset_id":"40mh8tv4k4ioam6kei9eo9cfv4","photourl":"","status":1,"asset_name":"热狗","created_at":"2018-11-27 09:23:54","custom_type_name":"办公用品","org_name":"弯弯的小公司","emp_name":"","user_name":"","status_cn":"闲置"},{"asset_code":"A02","asset_id":"rrd025nf319lsfb7vtshlda7ee","photourl":"","status":1,"asset_name":"圣诞帽","created_at":"2018-11-26 18:04:43","custom_type_name":"werre","org_name":"人力资源部","emp_name":"晏骁","user_name":"wanwan","status_cn":"闲置"}]
     * all_ids : ["rrd025nf319lsfb7vtshlda7ee","40mh8tv4k4ioam6kei9eo9cfv4","s4c6jvuoidcf5o7lgl16cmf4t6"]
     */

    private int total;
    private int page_size;
    private int page_count;
    private int current_page;
    private List<AssetBean> list;
    private List<String> all_ids;

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

    public List<String> getAll_ids() {
        return all_ids;
    }

    public void setAll_ids(List<String> all_ids) {
        this.all_ids = all_ids;
    }
}
