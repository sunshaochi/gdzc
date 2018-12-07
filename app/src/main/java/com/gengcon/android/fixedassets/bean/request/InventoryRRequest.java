package com.gengcon.android.fixedassets.bean.request;


public class InventoryRRequest {

    String inv_no;
    int status;
    int page;

    public InventoryRRequest(String inv_no, int status, int page) {
        this.inv_no = inv_no;
        this.status = status;
        this.page = page;
    }
}
