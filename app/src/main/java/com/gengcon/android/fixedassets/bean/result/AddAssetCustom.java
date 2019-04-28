package com.gengcon.android.fixedassets.bean.result;


import java.io.Serializable;
import java.util.List;

public class AddAssetCustom extends InvalidBean implements Serializable {

    /**
     * attr_id : 71vekk8g2vbs36bl85kf3juqan
     * attr_name : 颜色
     * attr_type : 4
     * default_data : ["红色","白色","黑色"]
     * is_required : 0
     * is_show : 1
     */

    private String attr_id;
    private String attr_name;
    private int attr_type;
    private int is_required;
    private int is_show;
    private List<String> default_data;
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

    public List<String> getDefault_data() {
        return default_data;
    }

    public void setDefault_data(List<String> default_data) {
        this.default_data = default_data;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
