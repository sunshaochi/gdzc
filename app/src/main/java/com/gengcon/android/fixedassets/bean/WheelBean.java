package com.gengcon.android.fixedassets.bean;

import java.io.Serializable;

public class WheelBean implements Serializable {

    int id;
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String user_name) {
        this.name = user_name;
    }

}
