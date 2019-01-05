package com.gengcon.android.fixedassets.bean;

import java.io.Serializable;

public class LoginUserBean implements Serializable {

    String token;
    String imgurl;
    int is_superadmin;

    public LoginUserBean(String token, String imgurl, int is_superadmin) {
        this.token = token;
        this.imgurl = imgurl;
        this.is_superadmin = is_superadmin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getIs_superadmin() {
        return is_superadmin;
    }

    public void setIs_superadmin(int is_superadmin) {
        this.is_superadmin = is_superadmin;
    }
}
