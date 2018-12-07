package com.gengcon.android.fixedassets.bean;

import java.io.Serializable;

public class UpdateImgRBean implements Serializable {

    String id;
    int index;

    public UpdateImgRBean(String id, int index) {
        this.id = id;
        this.index = index;
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
