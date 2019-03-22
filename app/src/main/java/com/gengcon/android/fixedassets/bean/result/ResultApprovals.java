package com.gengcon.android.fixedassets.bean.result;


import java.io.Serializable;
import java.util.List;

public class ResultApprovals extends InvalidBean implements Serializable {

    int total;
    int page_size;
    int page_count;
    int current_page;
    List<Approval> list;

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

    public List<Approval> getList() {
        return list;
    }

    public void setList(List<Approval> list) {
        this.list = list;
    }
}
