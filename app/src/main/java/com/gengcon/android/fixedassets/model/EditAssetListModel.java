package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.EditAssetListRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InventoryDetail;
import com.gengcon.android.fixedassets.htttp.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class EditAssetListModel extends BaseModel {

    ApiService.GetEditAssetListDetail editAssetListDetail = createService(ApiService.GetEditAssetListDetail.class);

    public Observable<Bean<InventoryDetail>> editAssetListDetail(String inv_no, List<String> add_assetids, List<String> del_assetids, int page) {
        return editAssetListDetail.getEditAssetListDetail(new EditAssetListRequest(inv_no, (ArrayList<String>) add_assetids, (ArrayList<String>) del_assetids, page, 30));
    }

}
