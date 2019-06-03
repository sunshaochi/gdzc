package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;
import java.util.List;

public class ClassificationBean extends InvalidBean implements Serializable {


    /**
     * id : 7274
     * pid : 0
     * custom_type_name : 土地、房屋及构筑物
     * level : 1
     * children : [{"id":27966,"pid":7280,"custom_type_name":"二级分类","level":2,"children":[{"id":30758,"pid":27966,"custom_type_name":"三级分类","level":3}]}]
     */

    private int id;
    private int pid;
    private String custom_type_name;
    private int level;
    private boolean disabled;
    private List<ClassificationBean> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getCustom_type_name() {
        return custom_type_name;
    }

    public void setCustom_type_name(String custom_type_name) {
        this.custom_type_name = custom_type_name;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<ClassificationBean> getChildren() {
        return children;
    }

    public void setChildren(List<ClassificationBean> children) {
        this.children = children;
    }
}
