package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.CreateInventoryRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class CreateInventoryModel extends BaseModel {

    ApiService.AddInventory addInventory = createService(ApiService.AddInventory.class);

    public Observable<Bean> addInventory(String docName, int userId, String remark, List<String> asset_ids) {
        return addInventory.addInventory(new CreateInventoryRequest(docName, userId, remark, (ArrayList<String>) asset_ids));
    }

}
