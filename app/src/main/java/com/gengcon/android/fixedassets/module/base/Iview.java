package com.gengcon.android.fixedassets.module.base;

public interface Iview<T> {

    void showLoading();

    void hideLoading();

    void showErrorMsg(int status, String msg);

    void showCodeMsg(String code, String msg);

    void showInvalidType(int invalid_type);
}