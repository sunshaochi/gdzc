package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class DeleteInventoryModel extends BaseModel {

    ApiService.DeleteInventory deleteInventory = createService(ApiService.DeleteInventory.class);

    public Observable<Bean> deleteInventory(String inv_no) {
        return deleteInventory.deleteInventory(inv_no);
    }

}
