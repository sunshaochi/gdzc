package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.request.DelEmpRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import io.reactivex.Observable;

public class DelEmpModel extends BaseModel {

    ApiService.GetDelEmp delEmp = createService(ApiService.GetDelEmp.class);

    public Observable<Bean> getDelEmp(int emp_id) {
        return delEmp.getDelEmp(new DelEmpRequest(emp_id));
    }
}
