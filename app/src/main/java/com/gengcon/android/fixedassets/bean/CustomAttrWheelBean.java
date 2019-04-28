package com.gengcon.android.fixedassets.bean;

import java.io.Serializable;

public class CustomAttrWheelBean implements Serializable {

    String id;
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String user_name) {
        this.name = user_name;
    }

}
