package com.gengcon.android.fixedassets.bean.request;


public class InventoryAssetRequest {

    String inv_no;
    int page;
    int limit;

    public InventoryAssetRequest(String inv_no, int page, int limit) {
        this.inv_no = inv_no;
        this.page = page;
        this.limit = limit;
    }
}
