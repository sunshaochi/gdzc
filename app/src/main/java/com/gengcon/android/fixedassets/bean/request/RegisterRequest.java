package com.gengcon.android.fixedassets.bean.request;


public class RegisterRequest {

    String user;
    String pwd;
    String phone;
    String company_name;
    int ind_id;

    public RegisterRequest(String user, String pwd, String phone, String company_name, int ind_id) {
        this.user = user;
        this.pwd = pwd;
        this.phone = phone;
        this.company_name = company_name;
        this.ind_id = ind_id;
    }
}
