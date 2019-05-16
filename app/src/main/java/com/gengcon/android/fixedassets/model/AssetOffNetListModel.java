package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.request.AssetOffNetRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultAsset;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import io.reactivex.Observable;

public class AssetOffNetListModel extends BaseModel {

    ApiService.GetAssetOffNetList assetOffNetList = createService(ApiService.GetAssetOffNetList.class);

    public Observable<Bean<ResultAsset>> getAssetOffNetList(String pd_no, int page) {
        return assetOffNetList.getAssetOffNetList(new AssetOffNetRequest(pd_no, page));
    }
}
