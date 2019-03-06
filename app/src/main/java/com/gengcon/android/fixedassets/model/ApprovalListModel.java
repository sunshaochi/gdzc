package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.ApprovalListBean;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class ApprovalListModel extends BaseModel {

    ApiService.GetApprovalList approvalList = createService(ApiService.GetApprovalList.class);

    public Observable<Bean<ApprovalListBean>> getApprovalList(int page) {
        return approvalList.getApprovalList(page);
    }
}
