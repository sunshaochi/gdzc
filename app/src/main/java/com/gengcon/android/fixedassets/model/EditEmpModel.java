package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class EditEmpModel extends BaseModel {

    ApiService.GetEditEmp editEmp = createService(ApiService.GetEditEmp.class);

    public Observable<Bean> getEditEmp(String json) {
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
        return editEmp.getEditEmp(requestBody);
    }
}
