package com.gengcon.android.fixedassets.bean;

import java.io.Serializable;

public class LoginUserBean implements Serializable {

    String token;
    String imgurl;
    int is_superadmin;
    String company_name;
    String user_id;

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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
