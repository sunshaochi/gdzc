package com.gengcon.android.fixedassets.util;

import com.gengcon.android.fixedassets.bean.result.ResultRole;

public class RolePowerManager {

    private ResultRole api_route;

    private static RolePowerManager mInstance;

    private RolePowerManager() {
    }

    public static RolePowerManager getInstance() {
        if (mInstance == null) {
            mInstance = new RolePowerManager();
        }
        return mInstance;
    }

    public void setApi_route(ResultRole api_route) {
        this.api_route = api_route;
    }

    //资产入库
    public boolean isAddModule() {
        if ((int) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.IS_SUPERADMIN, -1) == 1) {
            return true;
        } else {
            if (api_route != null) {
                return api_route.getAsset_module().isAllow();
            }
            return false;
        }
    }

    //待审批
    public boolean isAuditModule() {
        if ((int) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.IS_SUPERADMIN, -1) == 1) {
            return true;
        } else {
            if (api_route != null) {
                return api_route.getAudit_module().isAllow();
            }
            return false;
        }
    }

    //设置管理1组织
    public boolean isOrgModule() {
        if ((int) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.IS_SUPERADMIN, -1) == 1) {
            return true;
        } else {
            if (api_route != null) {
                return api_route.getOrg_module().isAllow();
            }
            return false;
        }
    }

    //设置管理2员工
    public boolean isEmpModule() {
        if ((int) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.IS_SUPERADMIN, -1) == 1) {
            return true;
        } else {
            if (api_route != null) {
                return api_route.getEmp_module().isAllow();
            }
            return false;
        }
    }

}
