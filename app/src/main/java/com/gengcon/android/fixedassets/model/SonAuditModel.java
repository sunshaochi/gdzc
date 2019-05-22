package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.request.SonAuditRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

public class SonAuditModel extends BaseModel {

    ApiService.GetSonAudit sonAudit = createService(ApiService.GetSonAudit.class);

    public Observable<Bean> getSonAudit(String pd_no, String remarks, List<String> asset_ids) {
        return sonAudit.getSonAudit(new SonAuditRequest(pd_no, remarks, asset_ids));
    }
}
