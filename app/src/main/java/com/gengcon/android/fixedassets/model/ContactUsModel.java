package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ContactUs;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class ContactUsModel extends BaseModel {

    ApiService.GetContactUs contactUs = createService(ApiService.GetContactUs.class);

    public Observable<Bean<ContactUs>> getContactUs() {
        return contactUs.getContactUs();
    }
}
