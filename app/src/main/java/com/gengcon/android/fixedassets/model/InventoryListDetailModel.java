package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.InventoryAssetRequest;
import com.gengcon.android.fixedassets.bean.result.InventoryDetail;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class InventoryListDetailModel extends BaseModel {

    ApiService.GetAssetListDetail getAssetListDetail = createService(ApiService.GetAssetListDetail.class);

    public Observable<Bean<InventoryDetail>> getListDetail(String inv_no, int page) {
        return getAssetListDetail.getInventoryDetail(new InventoryAssetRequest(inv_no, page));
    }
}
