package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.CheckRenameRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class CheckRenameModel extends BaseModel {

    ApiService.GetCheckRename checkRename = createService(ApiService.GetCheckRename.class);

    public Observable<Bean> getCheckRename(String user) {
        return checkRename.getCheckRename(new CheckRenameRequest(user));
    }
}
