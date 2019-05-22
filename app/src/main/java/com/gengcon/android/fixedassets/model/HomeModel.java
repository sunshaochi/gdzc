package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.Home;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class HomeModel extends BaseModel {

    ApiService.GetHome home = createService(ApiService.GetHome.class);

    public Observable<Bean<Home>> getHome() {
        return home.getHome();
    }
}
