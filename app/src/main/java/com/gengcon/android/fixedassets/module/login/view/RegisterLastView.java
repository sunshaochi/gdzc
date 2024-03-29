package com.gengcon.android.fixedassets.module.login.view;

import com.gengcon.android.fixedassets.module.base.Iview;
import com.gengcon.android.fixedassets.bean.result.Industry;
import com.gengcon.android.fixedassets.bean.result.UserData;

import java.util.List;

public interface RegisterLastView extends Iview {
    void showIndustry(List<Industry> industry);

    void checkRename();

    void completeRegister(UserData userData);
}
