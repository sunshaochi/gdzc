package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.AddAssetCustom;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

public class AddAsseCustomModel extends BaseModel {

    ApiService.GetCustomList customList = createService(ApiService.GetCustomList.class);

    public Observable<Bean<List<AddAssetCustom>>> getAddAssetCustom(String tpl_id) {
        return customList.getAddAssetCustomList(tpl_id);
    }
}
