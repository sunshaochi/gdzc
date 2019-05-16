package com.gengcon.android.fixedassets.bean.result;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SyncDataFailBean implements Serializable {

    @SerializedName("type")
    public int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
