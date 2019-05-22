package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

public class OrgSettingListModel extends BaseModel {

    ApiService.GetOrgSettingList orgSettingList = createService(ApiService.GetOrgSettingList.class);
    ApiService.GetOrgChildrenList orgChildrenList = createService(ApiService.GetOrgChildrenList.class);

    public Observable<Bean<List<OrgBean>>> getOrgSettingList(int pid) {
        if (pid == -1) {
            return orgSettingList.getOrgSettingList();
        } else {
            return orgChildrenList.getOrgChildrenList(pid);
        }
    }
}
