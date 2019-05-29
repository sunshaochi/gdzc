package com.gengcon.android.fixedassets.bean;

import java.io.Serializable;

public class LoginUserBean implements Serializable {

    String token;
    String imgurl;
    int is_superadmin;
    String company_name;
    String user_id;

    String file_domain;
    boolean is_demo;
    String phone;
    String root_org_id;
    String user_name;

    public LoginUserBean() {
    }

    public String getFile_domain() {
        return file_domain;
    }

    public void setFile_domain(String file_domain) {
        this.file_domain = file_domain;
    }

    public boolean getIs_demo() {
        return is_demo;
    }

    public void setIs_demo(boolean is_demo) {
        this.is_demo = is_demo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoot_org_id() {
        return root_org_id;
    }

    public void setRoot_org_id(String root_org_id) {
        this.root_org_id = root_org_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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
