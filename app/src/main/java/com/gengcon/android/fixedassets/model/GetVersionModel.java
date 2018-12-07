package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.UpdateVersion;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class GetVersionModel extends BaseModel {

    ApiService.GetVersion version = createService(ApiService.GetVersion.class);

    public Observable<Bean<UpdateVersion>> getVersion() {
        return version.getVersion();
    }
}
