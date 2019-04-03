package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiService;

import io.reactivex.Observable;

public class DeleteInventoryModel extends BaseModel {

    ApiService.DeleteInventory deleteInventory = createService(ApiService.DeleteInventory.class);

    public Observable<Bean> deleteInventory(String inv_no) {
        return deleteInventory.deleteInventory(inv_no);
    }

}
