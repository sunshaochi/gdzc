package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.UserPopupNotice;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class UserPopupNoticeModel extends BaseModel {

    ApiService.GetUserPopupNotice userPopupNotice = createService(ApiService.GetUserPopupNotice.class);

    public Observable<Bean<UserPopupNotice>> getUserPopupNotice() {
        return userPopupNotice.getUserPopupNotice();
    }
}
