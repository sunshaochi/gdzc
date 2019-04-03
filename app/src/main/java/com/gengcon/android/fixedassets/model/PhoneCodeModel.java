package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.PhoneCodeRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import io.reactivex.Observable;

public class PhoneCodeModel extends BaseModel {

    ApiService.GetPhoneCode phoneCode = createService(ApiService.GetPhoneCode.class);

    public Observable<Bean> getPhoneCode(String phone, String type) {
        return phoneCode.getPhoneCode(new PhoneCodeRequest(phone, type));
    }
}
