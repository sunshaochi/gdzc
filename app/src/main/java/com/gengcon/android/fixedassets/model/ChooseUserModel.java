package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ChooseUserBean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

public class ChooseUserModel extends BaseModel {

    ApiService.GetAssetUser assetUser = createService(ApiService.GetAssetUser.class);

    public Observable<Bean<List<ChooseUserBean>>> getAssetUser(String wd, String org_id) {
        return assetUser.getAssetUser(wd, org_id);
    }
}
