package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.CheckPhoneRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ForgetPwd;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class ForgetPhoneModel extends BaseModel {

    ApiService.GetResetPwdVerify resetPwdVerify = createService(ApiService.GetResetPwdVerify.class);

    public Observable<Bean<ForgetPwd>> getResetPwdVerify(String phone, String code) {
        return resetPwdVerify.getResetPwdVerify(new CheckPhoneRequest(phone, code));
    }
}
