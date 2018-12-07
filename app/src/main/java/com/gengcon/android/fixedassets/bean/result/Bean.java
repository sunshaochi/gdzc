package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class Bean<T> implements Serializable {

    String code;
    String msg;
    T data;


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

//    public String getMsg_arr() {
//        return msg_arr;
//    }
//
//    public void setMsg_arr(String msg_arr) {
//        this.msg_arr = msg_arr;
//    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
