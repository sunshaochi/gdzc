package com.gengcon.android.fixedassets.view;

import com.gengcon.android.fixedassets.base.Iview;
import com.gengcon.android.fixedassets.bean.result.Home;
import com.gengcon.android.fixedassets.bean.result.ResultRole;
import com.gengcon.android.fixedassets.bean.result.UserPopupNotice;

import java.util.List;

public interface HomeView extends Iview {

    void showHome(Home resultHome);

    void showApiRoute(ResultRole apiRoute);

    void showNotice(UserPopupNotice userPopupNotice);
}
