package com.gengcon.android.fixedassets.bean.request;


public class CheckPhoneRequest {

    String phone;
    String code;

    public CheckPhoneRequest(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }
}
