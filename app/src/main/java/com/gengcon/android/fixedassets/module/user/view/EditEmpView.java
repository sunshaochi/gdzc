package com.gengcon.android.fixedassets.module.user.view;

import com.gengcon.android.fixedassets.bean.result.StaffDetailBean;
import com.gengcon.android.fixedassets.module.base.Iview;

public interface EditEmpView extends Iview {
    void editEmpSuccess();

    void editEmpFail(String msg);

    void showEmpDetail(StaffDetailBean staffDetailBean);
}
