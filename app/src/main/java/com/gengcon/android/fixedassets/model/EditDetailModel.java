package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.EditAssetRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InventoryDetail;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import io.reactivex.Observable;

public class EditDetailModel extends BaseModel {

    ApiService.GetEditDetail editDetail = createService(ApiService.GetEditDetail.class);

    public Observable<Bean<InventoryDetail>> getEditDetail(String inv_no, int page) {
        return editDetail.getEditDetail(new EditAssetRequest(inv_no, page, 30));
    }
}
