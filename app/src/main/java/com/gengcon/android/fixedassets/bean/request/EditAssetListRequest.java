package com.gengcon.android.fixedassets.bean.request;

import java.util.ArrayList;

public class EditAssetListRequest {

    String inv_no;
    ArrayList<String> add_assetids;
    ArrayList<String> del_assetids;
    int page;

    public EditAssetListRequest(String inv_no, ArrayList<String> add_assetids, ArrayList<String> del_assetids, int page) {
        this.inv_no = inv_no;
        this.add_assetids = add_assetids;
        this.del_assetids = del_assetids;
        this.page = page;
    }
}
