package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.MessageBean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class UserNoticeModel extends BaseModel {

    ApiService.GetUserNotice userNotice = createService(ApiService.GetUserNotice.class);

    public Observable<Bean<MessageBean>> getUserNotice() {
        return userNotice.getUserNotice();
    }
}
