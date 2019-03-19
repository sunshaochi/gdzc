package com.gengcon.android.fixedassets.bean.result;

public class UserData {

    /**
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImp0aSI6IjVjOGY3Mjg5ODE5OTMifQ.eyJpc3MiOiIiLCJqdGkiOiI1YzhmNzI4OTgxOTkzIiwiaWF0IjoxNTUyOTA0ODQxLCJleHAiOjE1NTM1MDk2NDEsImxvZ2luZGF0YSI6eyJ1c2VyX2lkIjoiMTAzNTgiLCJwaG9uZSI6IjE4Nzc3Nzc3Nzc3Iiwicm9vdF9vcmdfaWQiOiI2MjkxOCIsImlzX3N1cGVyYWRtaW4iOjEsInJlZnJlc2hfdGltZSI6MTU1MjkwNDg0MX19.EqX7QHRDQ-tBuMMnEgtjs5frhb610_s6QViIfOiSarg
     * user_name : 卡乐莫
     * phone : 18777777777
     * is_superadmin : 1
     * root_org_id : 62918
     * file_domain : http://api.g.jc-test.cn
     */

    private String token;
    private String user_name;
    private String phone;
    private int is_superadmin;
    private String root_org_id;
    private String file_domain;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIs_superadmin() {
        return is_superadmin;
    }

    public void setIs_superadmin(int is_superadmin) {
        this.is_superadmin = is_superadmin;
    }

    public String getRoot_org_id() {
        return root_org_id;
    }

    public void setRoot_org_id(String root_org_id) {
        this.root_org_id = root_org_id;
    }

    public String getFile_domain() {
        return file_domain;
    }

    public void setFile_domain(String file_domain) {
        this.file_domain = file_domain;
    }
}
