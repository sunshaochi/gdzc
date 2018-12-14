package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class ErrorBean<T> implements Serializable {

    String code;
    String msg;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
