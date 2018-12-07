package com.gengcon.android.fixedassets.bean.request;


public class InventoryAssetRequest {

    String inv_no;
    int page;

    public InventoryAssetRequest(String inv_no, int page) {
        this.inv_no = inv_no;
        this.page = page;
    }
}
