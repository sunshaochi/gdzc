package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultRole;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class RouteModel extends BaseModel {

    ApiService.GetRoleResourceForApp roleResourceForApp = createService(ApiService.GetRoleResourceForApp.class);

    public Observable<Bean<ResultRole>> getApiRoute() {
        return roleResourceForApp.getRoleResourceForAPP();
    }

}
