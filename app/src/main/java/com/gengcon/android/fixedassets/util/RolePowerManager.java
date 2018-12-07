package com.gengcon.android.fixedassets.util;

import java.util.ArrayList;
import java.util.List;

public class RolePowerManager {

    private List<String> api_route = new ArrayList<>();

    private static RolePowerManager mInstance;

    private RolePowerManager() {
    }

    public static RolePowerManager getInstance() {
        if (mInstance == null) {
            mInstance = new RolePowerManager();
        }
        return mInstance;
    }

    public void setApi_route(List<String> api_route) {
        this.api_route = api_route;
    }

    public boolean check(String api) {
        for (int i = 0; i < api_route.size(); i++) {
            if (api.equals(api_route.get(i))) {
                return true;
            }
        }
        return true;
    }
}
