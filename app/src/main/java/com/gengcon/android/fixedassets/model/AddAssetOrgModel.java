package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

public class AddAssetOrgModel extends BaseModel {

    ApiService.GetOrgList orgList = createService(ApiService.GetOrgList.class);

    public Observable<Bean<List<OrgBean>>> getOrgList() {
        return orgList.getOrgList();
    }
}
