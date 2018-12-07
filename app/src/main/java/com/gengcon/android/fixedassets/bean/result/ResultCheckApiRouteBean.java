package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;
import java.util.List;

public class ResultCheckApiRouteBean implements Serializable {

    /**
     * code : CODE_200
     * msg : success
     * data : [false,false]
     */

    private List<Boolean> data;

    public List<Boolean> getData() {
        return data;
    }

    public void setData(List<Boolean> data) {
        this.data = data;
    }
}
