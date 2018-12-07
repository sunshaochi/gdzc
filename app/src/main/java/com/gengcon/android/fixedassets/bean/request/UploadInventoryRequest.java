package com.gengcon.android.fixedassets.bean.request;

import java.util.ArrayList;

public class UploadInventoryRequest {

    String inv_no;
    ArrayList<String> asset_ids;

    public UploadInventoryRequest(String inv_no, ArrayList<String> asset_ids) {
        this.inv_no = inv_no;
        this.asset_ids = asset_ids;
    }
}
