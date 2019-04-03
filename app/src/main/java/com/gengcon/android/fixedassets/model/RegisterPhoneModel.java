package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.CheckPhoneRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import io.reactivex.Observable;

public class RegisterPhoneModel extends BaseModel {

    ApiService.GetRegisterPhone registerPhone = createService(ApiService.GetRegisterPhone.class);

    public Observable<Bean> getRegisterPhone(String phone, String code) {
        return registerPhone.getRegisterPhone(new CheckPhoneRequest(phone, code));
    }
}
