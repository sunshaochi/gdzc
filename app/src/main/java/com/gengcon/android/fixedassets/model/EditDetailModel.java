package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.EditAssetRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InventoryDetail;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class EditDetailModel extends BaseModel {

    ApiService.GetEditDetail editDetail = createService(ApiService.GetEditDetail.class);

    public Observable<Bean<InventoryDetail>> getEditDetail(String inv_no, int page) {
        return editDetail.getEditDetail(new EditAssetRequest(inv_no, page));
    }
}
