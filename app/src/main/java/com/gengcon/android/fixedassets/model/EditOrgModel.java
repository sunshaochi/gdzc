package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class EditOrgModel extends BaseModel {

    ApiService.GetEditOrg editOrg = createService(ApiService.GetEditOrg.class);

    public Observable<Bean> getEditOrg(String json) {
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
        return editOrg.getEditOrg(requestBody);
    }
}
