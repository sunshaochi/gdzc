package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultInventorys;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class MyCreatInventoryListModel extends BaseModel {

    ApiService.GetMyCreateInventory myCreateInventory = createService(ApiService.GetMyCreateInventory.class);

    public Observable<Bean<ResultInventorys>> getMyCreateInventory(int page) {
        return myCreateInventory.getMyCreateInventory(page);
    }
}
