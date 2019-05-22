package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.ApprovalAssetRequest;
import com.gengcon.android.fixedassets.bean.result.ApprovalDetailBean;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class ApprovalAssetListModel extends BaseModel {

    ApiService.GetApprovalAssetList approvalAssetList = createService(ApiService.GetApprovalAssetList.class);

    public Observable<Bean<ApprovalDetailBean>> getApprovalAssetList(String doc_no, int page) {
        return approvalAssetList.getApprovalAssetList(new ApprovalAssetRequest(doc_no, page, 30));
    }
}
