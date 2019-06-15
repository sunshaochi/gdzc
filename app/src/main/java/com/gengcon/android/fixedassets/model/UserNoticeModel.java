package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.MessageBean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class UserNoticeModel extends BaseModel {

    ApiService.GetUserNotice userNotice = createService(ApiService.GetUserNotice.class);
    ApiService.GetUserNoticePda userNoticePda = createService(ApiService.GetUserNoticePda.class);

    public Observable<Bean<MessageBean>> getUserNotice(int type, int page) {
        if (type == 2) {
            return userNoticePda.getUserNoticePda(page);
        } else {
            return userNotice.getUserNotice(page);
        }
    }
}
