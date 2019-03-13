package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;
import java.util.List;

public class Home extends InvalidBean implements Serializable {


    /**
     * total : 17
     * free_num : 1
     * useing_num : 9
     * reports_data : [{"name":"闲置","value":"3"},{"name":"在用","value":"9"},{"name":"借出","value":"0"},{"name":"报废","value":"1"},{"name":"维修","value":"1"},{"name":"待审","value":"3"}]
     */

    private int total;
    private int free_num;
    private int useing_num;
    private List<ReportsDataBean> reports_data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFree_num() {
        return free_num;
    }

    public void setFree_num(int free_num) {
        this.free_num = free_num;
    }

    public int getUseing_num() {
        return useing_num;
    }

    public void setUseing_num(int useing_num) {
        this.useing_num = useing_num;
    }

    public List<ReportsDataBean> getReports_data() {
        return reports_data;
    }

    public void setReports_data(List<ReportsDataBean> reports_data) {
        this.reports_data = reports_data;
    }

    public static class ReportsDataBean {
        /**
         * name : 闲置
         * value : 3
         */

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
