package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultInventorys;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class MyTaskInventoryListModel extends BaseModel {

    ApiService.GetMyTaskInventory myTaskInventorys = createService(ApiService.GetMyTaskInventory.class);

    public Observable<Bean<ResultInventorys>> getMyTaskInventory(int page) {
        return myTaskInventorys.getMyTaskInventory(page);
    }
}
