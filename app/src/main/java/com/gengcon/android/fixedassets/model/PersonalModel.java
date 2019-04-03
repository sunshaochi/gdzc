package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.PersonalBean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import io.reactivex.Observable;

public class PersonalModel extends BaseModel {

    ApiService.GetPersonal personal = createService(ApiService.GetPersonal.class);

    public Observable<Bean<PersonalBean>> getPersonal() {
        return personal.getPersonal();
    }
}
