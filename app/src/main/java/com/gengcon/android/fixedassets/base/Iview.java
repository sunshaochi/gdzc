package com.gengcon.android.fixedassets.base;

public interface Iview<T> {

    void showLoading();

    void hideLoading();

    void showErrorMsg(int status, String msg);

    void showCodeMsg(String code, String msg);

}