package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.PrintTagRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import io.reactivex.Observable;

public class PrintTagModel extends BaseModel {

    ApiService.GetPrintTag printTag = createService(ApiService.GetPrintTag.class);

    public Observable<Bean> getPrintTag(String equipment_name) {
        return printTag.getPrintTag(new PrintTagRequest(equipment_name));
    }
}
