package com.gengcon.android.fixedassets.bean.result;


import java.io.Serializable;

public class AddAsset implements Serializable {

    private String attr_id;
    private String attr_name;
    private int attr_type;
    private String default_data;
    private int is_required;
    private int is_show;
    private String bind_api;
    private String content;

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public int getAttr_type() {
        return attr_type;
    }

    public void setAttr_type(int attr_type) {
        this.attr_type = attr_type;
    }

    public String getDefault_data() {
        return default_data;
    }

    public void setDefault_data(String default_data) {
        this.default_data = default_data;
    }

    public int getIs_required() {
        return is_required;
    }

    public void setIs_required(int is_required) {
        this.is_required = is_required;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public String getBind_api() {
        return bind_api;
    }

    public void setBind_api(String bind_api) {
        this.bind_api = bind_api;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
