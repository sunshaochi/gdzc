package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultRole implements Serializable {

    private List<String> api_route;

    public List<String> getApi_route() {
        if (api_route != null) {
            return api_route;
        }
        return new ArrayList<>();
    }

    public void setApi_route(List<String> api_route) {
        this.api_route = api_route;
    }
}
