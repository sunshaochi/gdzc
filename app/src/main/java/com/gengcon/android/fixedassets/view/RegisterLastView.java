package com.gengcon.android.fixedassets.view;

import com.gengcon.android.fixedassets.base.Iview;
import com.gengcon.android.fixedassets.bean.result.Industry;
import com.gengcon.android.fixedassets.bean.result.UserData;

import java.util.List;

public interface RegisterLastView extends Iview {
    void showIndustry(List<Industry> industry);

    void checkRename();

    void completeRegister(UserData userData);
}
