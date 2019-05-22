package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.ApprovalHeadDetail;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class ApprovalHeadModel extends BaseModel {

    ApiService.GetApprovalHeadDetail headDetail = createService(ApiService.GetApprovalHeadDetail.class);

    public Observable<Bean<ApprovalHeadDetail>> getHeadDetail(String doc_no) {
        return headDetail.getApprovalHeadDetail(doc_no);
    }
}
