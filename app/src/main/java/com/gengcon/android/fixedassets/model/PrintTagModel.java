package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class PrintTagModel extends BaseModel {

    ApiService.GetPrintTag printTag = createService(ApiService.GetPrintTag.class);

    public Observable<Bean> getPrintTag(String equipment_name) {
        return printTag.getPrintTag(equipment_name);
    }
}
