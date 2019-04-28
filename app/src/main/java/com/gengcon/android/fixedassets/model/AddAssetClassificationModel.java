package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ClassificationBean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

public class AddAssetClassificationModel extends BaseModel {

    ApiService.GetClassificationList classificationList = createService(ApiService.GetClassificationList.class);

    public Observable<Bean<List<ClassificationBean>>> getClassificationList() {
        return classificationList.getClassificationList();
    }
}
