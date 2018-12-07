package com.gengcon.android.fixedassets.bean.request;

import java.util.ArrayList;

public class PreviewRequest {

    String inv_no;
    int status;
    int page;
    ArrayList<String> asset_ids;

    public PreviewRequest(String inv_no, ArrayList asset_ids, int status, int page) {
        this.inv_no = inv_no;
        this.status = status;
        this.page = page;
        this.asset_ids = asset_ids;
    }
}
