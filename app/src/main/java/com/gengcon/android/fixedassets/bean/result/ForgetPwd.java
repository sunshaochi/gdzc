package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class ForgetPwd extends InvalidBean implements Serializable {


    private String secret_key;

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }
}
