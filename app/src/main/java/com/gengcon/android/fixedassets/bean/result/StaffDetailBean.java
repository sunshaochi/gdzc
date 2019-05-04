package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class StaffDetailBean extends InvalidBean implements Serializable {

    /**
     * id : 170813
     * py :
     * emp_name : 测试250
     * emp_no : 250
     * root_org_id : 11140
     * org_id : 63240
     * phone :
     * email :
     * status : 1
     * position :
     * is_delete : 0
     * org_name : 测试225
     */

    private int id;
    private String py;
    private String emp_name;
    private String emp_no;
    private int root_org_id;
    private int org_id;
    private String phone;
    private String email;
    private int status;
    private String position;
    private int is_delete;
    private String org_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
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

    public int getRoot_org_id() {
        return root_org_id;
    }

    public void setRoot_org_id(int root_org_id) {
        this.root_org_id = root_org_id;
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }
}
