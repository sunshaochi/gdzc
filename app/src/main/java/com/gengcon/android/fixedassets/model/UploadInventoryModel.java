package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.UploadInventoryRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class UploadInventoryModel extends BaseModel {

    ApiService.UploadInventoryResult uploadInventoryResult = createService(ApiService.UploadInventoryResult.class);

    public Observable<Bean> uploadInventoryResult(String inv_no, List<String> asset_ids) {
        return uploadInventoryResult.uploadInventoryResult(new UploadInventoryRequest(inv_no, (ArrayList<String>) asset_ids));
    }

}
