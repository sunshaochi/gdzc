package com.gengcon.android.fixedassets.bean.request;


import java.util.ArrayList;

public class AddAssetRequest {

    ArrayList<String> idList;

    public AddAssetRequest(ArrayList<String> id_list){
        this.idList = id_list;
    }
}
