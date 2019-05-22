package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import io.reactivex.Observable;

public class EmpNoModel extends BaseModel {

    ApiService.GetEmpNo empNo = createService(ApiService.GetEmpNo.class);

    public Observable<Bean> getEmpNo() {
        return empNo.getEmpNo();
    }
}
