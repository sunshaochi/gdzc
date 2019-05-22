package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.MessageBean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class UserNoticeModel extends BaseModel {

    ApiService.GetUserNotice userNotice = createService(ApiService.GetUserNotice.class);

    public Observable<Bean<MessageBean>> getUserNotice(int page) {
        return userNotice.getUserNotice(page);
    }
}
