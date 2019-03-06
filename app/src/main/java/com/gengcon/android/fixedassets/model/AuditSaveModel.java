package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.AuditSaveRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class AuditSaveModel extends BaseModel {

    ApiService.GetAuditSave auditSave = createService(ApiService.GetAuditSave.class);

    public Observable<Bean> getAuditSave(String doc_no, int result, String reason) {
        return auditSave.getAuditSave(new AuditSaveRequest(doc_no, result, reason));
    }
}
