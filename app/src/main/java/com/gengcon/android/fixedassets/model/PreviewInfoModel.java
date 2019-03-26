package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.PreviewRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.PreviewInfo;
import com.gengcon.android.fixedassets.htttp.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class PreviewInfoModel extends BaseModel {

    ApiService.PreviewInventoryInfo previewInventoryInfo = createService(ApiService.PreviewInventoryInfo.class);

    public Observable<Bean<PreviewInfo>> previewInventoryInfo(String inv_no, List<String> asset_ids, int status, int page) {
        return previewInventoryInfo.previewInventoryInfo(new PreviewRequest(inv_no, (ArrayList) asset_ids, status, page, 30));
    }

}
