package com.gengcon.android.fixedassets.bean.request;


import java.util.List;

public class SonAuditRequest {

    String pd_no;
    String remarks;
    List<String> asset_ids;

    public SonAuditRequest(String pd_no, String remarks, List<String> asset_ids) {
        this.pd_no = pd_no;
        this.remarks = remarks;
        this.asset_ids = asset_ids;
    }
}
