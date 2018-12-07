package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class UpdateVersion implements Serializable {

    /**
     * version_name :
     * version_number : 3.0
     * update_type : 1
     * update_date : 2018-11-30 15:36:29
     * update_content :
     * url :
     */

    private String version_name;
    private int version_number;
    private int update_type;
    private String update_date;
    private String update_content;
    private String url;

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public int getVersion_number() {
        return version_number;
    }

    public void setVersion_number(int version_number) {
        this.version_number = version_number;
    }

    public int getUpdate_type() {
        return update_type;
    }

    public void setUpdate_type(int update_type) {
        this.update_type = update_type;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getUpdate_content() {
        return update_content;
    }

    public void setUpdate_content(String update_content) {
        this.update_content = update_content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
