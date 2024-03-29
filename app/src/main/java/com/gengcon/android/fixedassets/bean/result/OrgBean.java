package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;
import java.util.List;

public class OrgBean extends InvalidBean implements Serializable {

    /**
     * id : 11141
     * pid : 11140
     * org_name : 总经办
     * level : 2
     * type : 2
     * paths : 0,11140
     * disabled : false
     * children : [{"id":50243,"pid":11141,"org_name":"pppp","level":3,"type":2,"paths":"0,11140,11141","disabled":false,"children":[{"id":63075,"pid":50243,"org_name":"二级","level":4,"type":2,"paths":"0,11140,11141,50243","disabled":false}]}]
     */

    private int id;
    private int pid;
    private String org_name;
    private String org_code;
    private int level;
    private int type;
    private String paths;
    private boolean disabled;
    private int employee_num;
    private List<OrgBean> children;

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

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getEmployee_num() {
        return employee_num;
    }

    public void setEmployee_num(int employee_num) {
        this.employee_num = employee_num;
    }

    public List<OrgBean> getChildren() {
        return children;
    }

    public void setChildren(List<OrgBean> children) {
        this.children = children;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    @Override
    public String toString() {
        return "OrgBean{" +
                "id=" + id +
                ", pid=" + pid +
                ", org_name='" + org_name + '\'' +
                ", org_code='" + org_code + '\'' +
                ", level=" + level +
                ", type=" + type +
                ", paths='" + paths + '\'' +
                ", disabled=" + disabled +
                ", employee_num=" + employee_num +
                ", children=" + children +
                '}';
    }
}
