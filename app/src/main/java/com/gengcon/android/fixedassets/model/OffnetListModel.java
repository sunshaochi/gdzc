package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultInventory;
import com.gengcon.android.fixedassets.common.module.http.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import io.reactivex.Observable;

public class OffnetListModel extends BaseModel {

    ApiService.GetOffnetList offnetList = createService(ApiService.GetOffnetList.class);

    public Observable<Bean<ResultInventory>> getOffnetList() {
        return offnetList.getOffnetList();
    }
}
