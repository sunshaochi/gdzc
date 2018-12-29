package com.gengcon.android.fixedassets.bean.request;


public class InventoryRRequest {

    String inv_no;
    int status;
    int page;
    int limit;

    public InventoryRRequest(String inv_no, int status, int page, int limit) {
        this.inv_no = inv_no;
        this.status = status;
        this.page = page;
        this.limit = limit;
    }
}
