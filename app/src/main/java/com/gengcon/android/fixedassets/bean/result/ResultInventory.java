package com.gengcon.android.fixedassets.bean.result;

import com.gengcon.android.fixedassets.module.greendao.InventoryBean;

import java.io.Serializable;
import java.util.List;

public class ResultInventory extends InvalidBean implements Serializable {


    private List<InventoryBean> list;

    public List<InventoryBean> getList() {
        return list;
    }

    public void setList(List<InventoryBean> list) {
        this.list = list;
    }
}
