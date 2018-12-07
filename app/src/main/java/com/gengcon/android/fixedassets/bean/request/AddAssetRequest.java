package com.gengcon.android.fixedassets.bean.request;

import com.gengcon.android.fixedassets.bean.AssetBean;

import java.util.ArrayList;

public class AddAssetRequest {

    ArrayList<AssetBean> checked;
    ArrayList<String> idList;

    public AddAssetRequest(ArrayList<AssetBean> checked,  ArrayList<String> id_list){
        this.checked = checked;
        this.idList = id_list;
    }

    public ArrayList<AssetBean> getChecked() {
        return checked;
    }

    public void setChecked(ArrayList<AssetBean> checked) {
        this.checked = checked;
    }

    public ArrayList<String> getIdList() {
        return idList;
    }

    public void setIdList(ArrayList<String> id_list) {
        this.idList = id_list;
    }
}
