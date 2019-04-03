package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.ApprovalNum;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import io.reactivex.Observable;

public class ApprovalNumModel extends BaseModel {

    ApiService.GetApprovalNum approvalNum = createService(ApiService.GetApprovalNum.class);

    public Observable<Bean<ApprovalNum>> getApprovalNum() {
        return approvalNum.getApprovalNum();
    }
}
