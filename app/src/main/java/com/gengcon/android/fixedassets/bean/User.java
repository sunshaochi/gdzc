package com.gengcon.android.fixedassets.bean;

import java.io.Serializable;

public class User implements Serializable {

    int id;
    String user_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}
