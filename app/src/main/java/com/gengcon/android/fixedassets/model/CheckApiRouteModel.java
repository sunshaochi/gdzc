package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.CheckApiRouteRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class CheckApiRouteModel extends BaseModel {

    ApiService.CheckApiRoute checkApiRoute = createService(ApiService.CheckApiRoute.class);

    public Observable<Bean<List<Boolean>>> checkApiRoute(List<String> api_route) {
        return checkApiRoute.checkApiRoute(new CheckApiRouteRequest((ArrayList) api_route));
    }

}
