package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.ApprovalNum;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class ApprovalNumModel extends BaseModel {

    ApiService.GetApprovalNum approvalNum = createService(ApiService.GetApprovalNum.class);

    public Observable<Bean<ApprovalNum>> getApprovalNum() {
        return approvalNum.getApprovalNum();
    }
}
