package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class ReadAddNoticeModel extends BaseModel {

    ApiService.GetReadAddNotice readAddNotice = createService(ApiService.GetReadAddNotice.class);

    public Observable<Bean> getReadAddNotice(int notice_id) {
        return readAddNotice.getReadAddNotice(notice_id);
    }
}
