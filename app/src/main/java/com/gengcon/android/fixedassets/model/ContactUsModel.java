package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ContactUs;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class ContactUsModel extends BaseModel {

    ApiService.GetContactUs contactUs = createService(ApiService.GetContactUs.class);

    public Observable<Bean<ContactUs>> getContactUs() {
        return contactUs.getContactUs();
    }
}
