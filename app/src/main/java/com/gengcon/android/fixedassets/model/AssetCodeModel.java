package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.AssetCode;
import com.gengcon.android.fixedassets.common.module.http.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import io.reactivex.Observable;

public class AssetCodeModel extends BaseModel {

    ApiService.GetAssetCode assetCode = createService(ApiService.GetAssetCode.class);

    public Observable<AssetCode> getAssetCode() {
        return assetCode.getAssetCode();
    }
}
