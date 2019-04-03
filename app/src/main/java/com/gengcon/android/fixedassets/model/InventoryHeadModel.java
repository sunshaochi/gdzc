package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.InventoryHeadDetail;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import io.reactivex.Observable;

public class InventoryHeadModel extends BaseModel {

    ApiService.GetHeadDetail getDetail = createService(ApiService.GetHeadDetail.class);

    public Observable<Bean<InventoryHeadDetail>> getHeadDetail(String inv_no) {
        return getDetail.getHeadDetail(inv_no);
    }
}
