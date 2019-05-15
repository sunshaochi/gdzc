package com.gengcon.android.fixedassets.bean.request;


public class AssetOffNetRequest {

    String pd_no;
    int page;

    public AssetOffNetRequest(String pd_no, int page) {
        this.pd_no = pd_no;
        this.page = page;
    }
}
