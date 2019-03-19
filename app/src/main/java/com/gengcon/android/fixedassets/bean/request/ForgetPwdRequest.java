package com.gengcon.android.fixedassets.bean.request;


public class ForgetPwdRequest {

    String secret_key;
    String new_pwd;
    String new_repwd;

    public ForgetPwdRequest(String secret_key, String new_pwd, String new_repwd) {
        this.secret_key = secret_key;
        this.new_pwd = new_pwd;
        this.new_repwd = new_repwd;
    }
}
