package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.request.AssetSyncDataRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

public class AssetSyncDataModel extends BaseModel {

    ApiService.GetAssetSyncData assetSyncData = createService(ApiService.GetAssetSyncData.class);

    public Observable<Bean> getAssetSyncData(String pd_no, List<String> asset_ids) {
        return assetSyncData.getAssetSyncData(new AssetSyncDataRequest(pd_no, asset_ids));
    }
}
