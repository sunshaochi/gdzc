package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.base.BaseModel;
import com.gengcon.android.fixedassets.bean.request.ModifyPasswordRequest;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.ApiService;

import io.reactivex.Observable;

public class ModifyPasswordModel extends BaseModel {

    ApiService.GetModifyPassword modifyPassword = createService(ApiService.GetModifyPassword.class);

    public Observable<Bean> getModifyPassword(String old_pwd, String new_pwd, String new_repwd) {
        return modifyPassword.getModifyPassword(new ModifyPasswordRequest(old_pwd, new_pwd, new_repwd));
    }
}
