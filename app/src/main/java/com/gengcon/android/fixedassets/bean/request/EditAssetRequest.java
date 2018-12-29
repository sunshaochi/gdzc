package com.gengcon.android.fixedassets.bean.request;


public class EditAssetRequest {

    String inv_no;
    int page;
    int limit;

    public EditAssetRequest(String inv_no, int page, int limit) {
        this.inv_no = inv_no;
        this.page = page;
        this.limit = limit;
    }
}
