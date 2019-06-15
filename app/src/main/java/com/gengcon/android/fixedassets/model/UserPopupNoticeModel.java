package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.UserPopupNotice;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class UserPopupNoticeModel extends BaseModel {

    ApiService.GetUserPopupNotice userPopupNotice = createService(ApiService.GetUserPopupNotice.class);
    ApiService.GetUserPopupNoticePda userPopupNoticePda = createService(ApiService.GetUserPopupNoticePda.class);

    public Observable<Bean<UserPopupNotice>> getUserPopupNotice(int type) {
        if (type == 2) {
            return userPopupNoticePda.getUserPopupNoticePda();
        } else {
            return userPopupNotice.getUserPopupNotice();
        }
    }
}
