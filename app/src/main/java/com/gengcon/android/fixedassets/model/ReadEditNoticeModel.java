package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class ReadEditNoticeModel extends BaseModel {

    ApiService.GetReadEditNotice readEditNotice = createService(ApiService.GetReadEditNotice.class);

    public Observable<Bean> getReadEditNotice(int notice_id) {
        return readEditNotice.getReadEditNotice(notice_id);
    }
}
