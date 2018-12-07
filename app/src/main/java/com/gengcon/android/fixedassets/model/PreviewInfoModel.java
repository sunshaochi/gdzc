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
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("doc_id", docId);
//        map.put("status", status);
//        map.put("page", page);
//        map.put("assets_ids", new Gson().toJson(assetIds));
//        Log.e("PreviewInfoModel", "previewInventoryInfo: ");
        return previewInventoryInfo.previewInventoryInfo(new PreviewRequest(inv_no, (ArrayList) asset_ids, status, page));
//        return previewInventoryInfo.previewInventoryInfo(docId, status, page, new Gson().toJson(assetIds));
    }

}
