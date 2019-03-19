package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class Industry extends InvalidBean implements Serializable {


    /**
     * id : 1
     * industry_name : 农、林、牧、渔业
     */

    private int id;
    private String industry_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndustry_name() {
        return industry_name;
    }

    public void setIndustry_name(String industry_name) {
        this.industry_name = industry_name;
    }
}
