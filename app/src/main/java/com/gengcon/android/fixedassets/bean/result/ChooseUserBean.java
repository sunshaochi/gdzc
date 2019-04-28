package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class ChooseUserBean extends InvalidBean implements Serializable {


    /**
     * id : 156770
     * emp_name : 测试1
     * org_name : 子部门
     */

    private int id;
    private String emp_name;
    private String org_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }
}
