package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class EmpBean implements Serializable {


    /**
     * id : 170813
     * emp_name : 测试250
     * emp_no : 250
     * phone :
     * status : 1
     * position :
     * org_id : 63240
     * org_name : 测试225
     */

    private int id;
    private String emp_name;
    private String emp_no;
    private String phone;
    private int status;
    private String position;
    private int org_id;
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

    public String getEmp_no() {
        return emp_no;
    }

    public void setEmp_no(String emp_no) {
        this.emp_no = emp_no;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }
}
