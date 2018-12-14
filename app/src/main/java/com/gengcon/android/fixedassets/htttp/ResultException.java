package com.gengcon.android.fixedassets.htttp;

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
}
