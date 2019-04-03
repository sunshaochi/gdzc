package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.RegisterRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.UserData;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import io.reactivex.Observable;

public class RegisterModel extends BaseModel {

    ApiService.GetRegister register = createService(ApiService.GetRegister.class);

    public Observable<Bean<UserData>> getRegister(String user, String pwd, String phone, String company_name, int ind_id) {
        return register.getRegister(new RegisterRequest(user, pwd, phone, company_name, ind_id));
    }
}
