package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.PhoneCodeRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class PhoneCodeModel extends BaseModel {

    ApiService.GetPhoneCode phoneCode = createService(ApiService.GetPhoneCode.class);

    public Observable<Bean> getPhoneCode(String phone, String type) {
        return phoneCode.getPhoneCode(new PhoneCodeRequest(phone, type));
    }
}
