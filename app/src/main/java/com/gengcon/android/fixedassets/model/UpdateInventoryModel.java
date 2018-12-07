package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.UpdateInventoryRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class UpdateInventoryModel extends BaseModel {

    ApiService.UpdateInventory updateInventory = createService(ApiService.UpdateInventory.class);

    public Observable<Bean> updateInventory(String inv_no, String inv_name, int allot_userid, String remark, List<String> add_assetids, List<String> del_assetids) {
//        return addInventory.addInventory(docName, userId, remark, new Gson().toJson(asset_ids));
        return updateInventory.updateInventory(new UpdateInventoryRequest(inv_no, inv_name, allot_userid, remark, (ArrayList<String>) add_assetids, (ArrayList<String>) del_assetids));
    }

}
