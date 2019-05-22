package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class DelOrgModel extends BaseModel {

    ApiService.GetDelOrg delOrg = createService(ApiService.GetDelOrg.class);

    public Observable<Bean> getDelOrg(String json) {
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
        return delOrg.getDelOrg(requestBody);
    }
}
