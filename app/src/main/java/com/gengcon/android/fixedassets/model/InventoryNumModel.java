package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultInventorysNum;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import io.reactivex.Observable;

public class InventoryNumModel extends BaseModel {

    ApiService.GetInventorysNum inventorysNum = createService(ApiService.GetInventorysNum.class);

    public Observable<Bean<ResultInventorysNum>> getInventorysNum() {
        return inventorysNum.getInventorysNum();
    }
}
