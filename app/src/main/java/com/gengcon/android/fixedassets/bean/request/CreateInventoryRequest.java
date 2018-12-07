package com.gengcon.android.fixedassets.bean.request;

import java.util.ArrayList;

public class CreateInventoryRequest {

    String inv_name;
    int allot_userid;
    String remark;
    ArrayList<String> add_assetids;

    public CreateInventoryRequest(String inv_name, int allot_userid, String remark, ArrayList<String> add_assetids) {
        this.inv_name = inv_name;
        this.allot_userid = allot_userid;
        this.remark = remark;
        this.add_assetids = add_assetids;
    }
}
