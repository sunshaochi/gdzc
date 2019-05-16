package com.gengcon.android.fixedassets.bean.result;

import com.gengcon.android.fixedassets.module.greendao.AssetBean;

import java.io.Serializable;
import java.util.List;

public class ResultAsset extends InvalidBean implements Serializable {

    /**
     * total : 23
     * page_size : 2000
     * page_count : 1
     * current_page : 1
     * list : []
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
