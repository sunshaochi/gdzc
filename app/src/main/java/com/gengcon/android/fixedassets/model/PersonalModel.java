package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.PersonalBean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class PersonalModel extends BaseModel {

    ApiService.GetPersonal personal = createService(ApiService.GetPersonal.class);

    public Observable<Bean<PersonalBean>> getPersonal() {
        return personal.getPersonal();
    }
}
