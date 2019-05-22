package com.gengcon.android.fixedassets.model;

import com.gengcon.android.fixedassets.module.base.BaseModel;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.common.module.http.ApiService;

import java.util.List;

import io.reactivex.Observable;

public class UsersModel extends BaseModel {

    ApiService.GetUsers loadUsers = createService(ApiService.GetUsers.class);

    public Observable<Bean<List<User>>> getUsers() {
        return loadUsers.getUsers();
    }
}
