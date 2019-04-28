package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.AddAssetList;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import io.reactivex.Observable;

public class AddAssetListModel extends BaseModel {

    ApiService.GetAssetAddList assetAddList = createService(ApiService.GetAssetAddList.class);

    public Observable<Bean<AddAssetList>> getAddAssetList() {
        return assetAddList.getAddAssetList();
    }
}
