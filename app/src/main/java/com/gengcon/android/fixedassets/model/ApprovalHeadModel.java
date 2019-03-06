package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.ApprovalHeadDetail;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import java.util.List;

import io.reactivex.Observable;

public class ApprovalHeadModel extends BaseModel {

    ApiService.GetApprovalHeadDetail headDetail = createService(ApiService.GetApprovalHeadDetail.class);

    public Observable<Bean<List<ApprovalHeadDetail>>> getHeadDetail(String doc_no) {
        return headDetail.getApprovalHeadDetail(doc_no);
    }
}
