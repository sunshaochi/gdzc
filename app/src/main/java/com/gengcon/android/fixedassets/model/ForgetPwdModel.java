package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.ForgetPwdRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import io.reactivex.Observable;

public class ForgetPwdModel extends BaseModel {

    ApiService.GetResetPwd resetPwd = createService(ApiService.GetResetPwd.class);

    public Observable<Bean> getResetPwd(String secret_key, String new_pwd, String new_repwd) {
        return resetPwd.getResetPwd(new ForgetPwdRequest(secret_key, new_pwd, new_repwd));
    }
}
