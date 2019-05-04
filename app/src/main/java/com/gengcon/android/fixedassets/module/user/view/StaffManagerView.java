package com.gengcon.android.fixedassets.module.user.view;

import com.gengcon.android.fixedassets.bean.result.StaffManagerBean;
import com.gengcon.android.fixedassets.module.base.Iview;

public interface StaffManagerView extends Iview {
    void showStaff(StaffManagerBean staffManagerBean);

    void onFail(String msg);

}
