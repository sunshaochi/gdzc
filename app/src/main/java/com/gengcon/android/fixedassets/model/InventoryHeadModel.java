package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.InventoryHeadDetail;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class InventoryHeadModel extends BaseModel {

    ApiService.GetHeadDetail getDetail = createService(ApiService.GetHeadDetail.class);

    public Observable<Bean<InventoryHeadDetail>> getHeadDetail(String inv_no) {
        return getDetail.getHeadDetail(inv_no);
    }
}
