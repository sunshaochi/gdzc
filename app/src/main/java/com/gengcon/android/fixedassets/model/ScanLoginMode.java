package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class ScanLoginMode extends BaseModel {

    ApiService.ScanLogin scanLogin = createService(ApiService.ScanLogin.class);

    public Observable<Bean> scanLogin(String uuid, int login_status) {
        return scanLogin.scanLogin(uuid, login_status);
    }
}
