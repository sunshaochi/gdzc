package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.CheckPhoneRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import io.reactivex.Observable;

public class CheckOldPhoneModel extends BaseModel {

    ApiService.GetCheckOldPhone checkOldPhone = createService(ApiService.GetCheckOldPhone.class);

    public Observable<Bean> getCheckOldPhone(String phone, String code) {
        return checkOldPhone.getCheckOldPhone(new CheckPhoneRequest(phone, code));
    }
}
