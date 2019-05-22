package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultApprovals;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class WaitApprovalListModel extends BaseModel {

    ApiService.GetWaitApprovalList waitApprovalList = createService(ApiService.GetWaitApprovalList.class);

    public Observable<Bean<ResultApprovals>> getWaitApprovalList(int page) {
        return waitApprovalList.getWaitApprovalList(page, 30);
    }
}
