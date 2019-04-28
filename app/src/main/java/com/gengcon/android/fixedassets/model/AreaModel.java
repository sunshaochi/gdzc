package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.bean.Area;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;
import com.gengcon.android.fixedassets.module.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

public class AreaModel extends BaseModel {

    ApiService.GetArea area = createService(ApiService.GetArea.class);

    public Observable<Bean<List<Area>>> getArea() {
        return area.getArea();
    }
}
