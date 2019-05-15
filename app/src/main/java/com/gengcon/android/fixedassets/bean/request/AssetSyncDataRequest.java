package com.gengcon.android.fixedassets.bean.request;


import java.util.List;

public class AssetSyncDataRequest {

    String pd_no;
    List<String> asset_ids;

    public AssetSyncDataRequest(String pd_no, List<String> asset_ids) {
        this.pd_no = pd_no;
        this.asset_ids = asset_ids;
    }
}
