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

    //资产管理
    public boolean isAssetModule() {
        if (api_route != null) {
            return api_route.getAsset_module().isAllow();
        }
        return false;
    }

    //资产入库
    public boolean isAddModule() {
        if (api_route != null) {
            return api_route.getAsset_add_module().isAllow();
        }
        return false;
    }

    //盘点管理
    public boolean isInventoryModule() {
        if (api_route != null) {
            return api_route.getInventory_module().isAllow();
        }
        return false;
    }

    //处理记录
    public boolean isDocModule() {
        if (api_route != null) {
            return api_route.getDoc_module().isAllow();
        }
        return false;
    }

    //分析报表
    public boolean isReportModule() {
        if (api_route != null) {
            return api_route.getReport_module().isAllow();
        }
        return false;
    }

    //设置管理1组织
    public boolean isOrgModule() {
        if (api_route != null) {
            return api_route.getOrg_module().isAllow();
        }
        return false;
    }

    //设置管理2员工
    public boolean isEmpModule() {
        if (api_route != null) {
            return api_route.getEmp_module().isAllow();
        }
        return false;
    }

    //删除盘点单
    public boolean isInventoryDel() {
        if (api_route != null) {
            return api_route.getInventory_del().isAllow();
        }
        return false;
    }

    //新建盘点单
    public boolean isInventoryAdd() {
        if (api_route != null) {
            return api_route.getInventory_add().isAllow();
        }
        return false;
    }

    //编辑盘点单
    public boolean isInventoryEdit() {
        if (api_route != null) {
            return api_route.getInventory_edit().isAllow();
        }
        return false;
    }

    //盘点权限
    public boolean isInventoryPd() {
        if (api_route != null) {
            return api_route.getInventory_pd().isAllow();
        }
        return false;
    }

}
