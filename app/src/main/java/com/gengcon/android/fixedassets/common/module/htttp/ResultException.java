package com.gengcon.android.fixedassets.common.module.htttp;

import java.io.IOException;

public class ResultException extends IOException {
    String code;
    String msg;

    public ResultException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
