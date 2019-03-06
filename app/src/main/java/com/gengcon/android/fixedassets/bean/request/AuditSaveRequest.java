package com.gengcon.android.fixedassets.bean.request;


public class AuditSaveRequest {

    String doc_no;
    int result;
    String reason;

    public AuditSaveRequest(String doc_no, int result, String reason) {
        this.doc_no = doc_no;
        this.result = result;
        this.reason = reason;
    }
}
