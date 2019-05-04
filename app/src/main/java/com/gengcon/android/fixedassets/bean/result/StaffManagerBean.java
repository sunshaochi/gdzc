package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;
import java.util.List;

public class StaffManagerBean extends InvalidBean implements Serializable {


    private List<OrgBean> org_data;
    private List<EmpBean> emp_data;

    public List<OrgBean> getOrg_data() {
        return org_data;
    }

    public void setOrg_data(List<OrgBean> org_data) {
        this.org_data = org_data;
    }

    public List<EmpBean> getEmp_data() {
        return emp_data;
    }

    public void setEmp_data(List<EmpBean> emp_data) {
        this.emp_data = emp_data;
    }
}
