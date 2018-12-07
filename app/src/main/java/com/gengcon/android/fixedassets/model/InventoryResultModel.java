package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.InventoryRRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InventoryR;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class InventoryResultModel extends BaseModel {

    ApiService.ShowInventoryResult inventoryResult = createService(ApiService.ShowInventoryResult.class);

    public Observable<Bean<InventoryR>> showInventoryResult(String inv_no, int status, int page) {
        return inventoryResult.showInventoryResult(new InventoryRRequest(inv_no, status, page));
    }
}
