package com.gengcon.android.fixedassets.bean;

import java.io.Serializable;

public class UpdateImgRBean implements Serializable {

    String id;
    int index;
    int type;

    public UpdateImgRBean(int type, String id, int index) {
        this.type = type;
        this.id = id;
        this.index = index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
