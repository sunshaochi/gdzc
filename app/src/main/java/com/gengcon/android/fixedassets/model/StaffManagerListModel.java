package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.StaffManagerBean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import io.reactivex.Observable;

public class StaffManagerListModel extends BaseModel {

    ApiService.GetStaffManagerList staffManagerList = createService(ApiService.GetStaffManagerList.class);
    ApiService.GetStaffChildrenList childrenList = createService(ApiService.GetStaffChildrenList.class);

    public Observable<Bean<StaffManagerBean>> getStaffManagerList(int pid) {
        if (pid == -1) {
            return staffManagerList.getStaffManagerList();
        } else {
            return childrenList.getStaffChildrenList(pid);
        }
    }
}
