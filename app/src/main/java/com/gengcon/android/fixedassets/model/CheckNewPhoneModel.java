package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.CheckPhoneRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class CheckNewPhoneModel extends BaseModel {

    ApiService.GetCheckNewPhone checkNewPhone = createService(ApiService.GetCheckNewPhone.class);

    public Observable<Bean> getCheckNewPhone(String phone, String code) {
        return checkNewPhone.getCheckNewPhone(new CheckPhoneRequest(phone, code));
    }
}
