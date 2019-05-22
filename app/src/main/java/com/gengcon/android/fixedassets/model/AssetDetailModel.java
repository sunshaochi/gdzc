package com.gengcon.android.fixedassets.model;


import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class AssetDetailModel extends BaseModel {

    ApiService.AssetDetail assetDetail = createService(ApiService.AssetDetail.class);

    public Observable<Bean> assetDetail(String asset_id) {
        return assetDetail.getAssetDetail(asset_id);
    }

}
