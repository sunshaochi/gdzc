package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.Industry;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import java.util.List;

import io.reactivex.Observable;

public class IndustryModel extends BaseModel {

    ApiService.GetIndustry industry = createService(ApiService.GetIndustry.class);

    public Observable<Bean<List<Industry>>> getIndustry() {
        return industry.getIndustry();
    }
}
