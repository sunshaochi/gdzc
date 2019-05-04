package com.gengcon.android.fixedassets.module.user.view;

import com.gengcon.android.fixedassets.bean.result.StaffDetailBean;
import com.gengcon.android.fixedassets.module.base.Iview;

public interface StaffDetailView extends Iview {
    void showStaffDetail(StaffDetailBean data);
    void showDelEmp();
}
