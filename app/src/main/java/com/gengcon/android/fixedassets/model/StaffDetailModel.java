package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.StaffDetailBean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import io.reactivex.Observable;

public class StaffDetailModel extends BaseModel {

    ApiService.GetStaffDetail staffDetail = createService(ApiService.GetStaffDetail.class);

    public Observable<Bean<StaffDetailBean>> getStaffDetail(int emp_id) {
        return staffDetail.getStaffDetail(emp_id);
    }
}
