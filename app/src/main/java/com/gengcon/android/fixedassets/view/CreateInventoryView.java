package com.gengcon.android.fixedassets.view;

import com.gengcon.android.fixedassets.base.Iview;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.result.Bean;

import java.util.List;

public interface CreateInventoryView extends Iview {

    void addInventoryResult(Bean resultBean);

    void initUsers(List<User> user);

    void contractExpire();
}
