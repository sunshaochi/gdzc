package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.Home;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class HomeModel extends BaseModel {

    ApiService.GetHome home = createService(ApiService.GetHome.class);

    public Observable<Bean<Home>> getHome() {
        return home.getHome();
    }
}
