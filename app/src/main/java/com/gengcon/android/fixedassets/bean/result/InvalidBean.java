package com.gengcon.android.fixedassets.bean.result;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InvalidBean implements Serializable {

    @SerializedName("invalid_type")
    public int invalid_type;

    public int getInvalid_type() {
        return invalid_type;
    }

    public void setInvalid_type(int invalid_type) {
        this.invalid_type = invalid_type;
    }
}
