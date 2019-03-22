package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultApprovals;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class CompleteApprovalListModel extends BaseModel {

    ApiService.GetCompleteApprovalList completeApprovalList = createService(ApiService.GetCompleteApprovalList.class);

    public Observable<Bean<ResultApprovals>> getCompleteApprovalList(int page) {
        return completeApprovalList.getCompleteApprovalList(page,30);
    }
}
