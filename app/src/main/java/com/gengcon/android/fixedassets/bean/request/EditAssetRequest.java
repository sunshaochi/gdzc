package com.gengcon.android.fixedassets.bean.request;


public class EditAssetRequest {

    String inv_no;
    int page;

    public EditAssetRequest(String inv_no, int page) {
        this.inv_no = inv_no;
        this.page = page;
    }
}
