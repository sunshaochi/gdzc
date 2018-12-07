package com.gengcon.android.fixedassets.bean.request;

import java.util.ArrayList;

public class UpdateInventoryRequest {

    String inv_name;
    String inv_no;
    int allot_userid;
    String remark;
    ArrayList<String> add_assetids;
    ArrayList<String> del_assetids;

    public UpdateInventoryRequest(String inv_no, String inv_name, int allot_userid, String remark, ArrayList<String> add_assetids, ArrayList<String> del_assetids) {
        this.inv_no = inv_no;
        this.inv_name = inv_name;
        this.allot_userid = allot_userid;
        this.remark = remark;
        this.add_assetids = add_assetids;
        this.del_assetids = del_assetids;
    }
}
