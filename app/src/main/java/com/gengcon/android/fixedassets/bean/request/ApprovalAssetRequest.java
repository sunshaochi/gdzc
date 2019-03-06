package com.gengcon.android.fixedassets.bean.request;


public class ApprovalAssetRequest {

    String doc_no;
    int page;
    int limit;

    public ApprovalAssetRequest(String doc_no, int page, int limit) {
        this.doc_no = doc_no;
        this.page = page;
        this.limit = limit;
    }
}
