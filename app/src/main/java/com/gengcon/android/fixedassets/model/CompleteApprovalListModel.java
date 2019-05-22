package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultApprovals;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class CompleteApprovalListModel extends BaseModel {

    ApiService.GetCompleteApprovalList completeApprovalList = createService(ApiService.GetCompleteApprovalList.class);

    public Observable<Bean<ResultApprovals>> getCompleteApprovalList(int page) {
        return completeApprovalList.getCompleteApprovalList(page,30);
    }
}
