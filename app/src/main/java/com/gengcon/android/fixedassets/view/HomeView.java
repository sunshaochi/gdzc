package com.gengcon.android.fixedassets.view;

import com.gengcon.android.fixedassets.base.Iview;
import com.gengcon.android.fixedassets.bean.result.Home;
import com.gengcon.android.fixedassets.bean.result.ResultRole;

import java.util.List;

public interface HomeView extends Iview {

    void showHome(Home resultHome);

    void checkApiRoute(List<Boolean> data);

    void showApiRoute(ResultRole apiRoute);
}
