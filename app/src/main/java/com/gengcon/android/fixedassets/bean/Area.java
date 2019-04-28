package com.gengcon.android.fixedassets.bean;

import java.io.Serializable;

public class Area implements Serializable {

    /**
     * area_id : 71
     * pid : 0
     * area_name : 蚕丝洞
     * area_code : 1000
     * sort_num : 1
     */

    private int area_id;
    private int pid;
    private String area_name;
    private String area_code;
    private int sort_num;

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public int getSort_num() {
        return sort_num;
    }

    public void setSort_num(int sort_num) {
        this.sort_num = sort_num;
    }
}
