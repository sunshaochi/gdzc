package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultInventorysNum;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class InventoryNumModel extends BaseModel {

    ApiService.GetInventorysNum inventorysNum = createService(ApiService.GetInventorysNum.class);

    public Observable<Bean<ResultInventorysNum>> getInventorysNum() {
        return inventorysNum.getInventorysNum();
    }
}
