package com.gengcon.android.fixedassets.bean.request;


public class ModifyPasswordRequest {

    String old_pwd;
    String new_pwd;
    String new_repwd;

    public ModifyPasswordRequest(String old_pwd, String new_pwd, String new_repwd) {
        this.old_pwd = old_pwd;
        this.new_pwd = new_pwd;
        this.new_repwd = new_repwd;
    }
}
