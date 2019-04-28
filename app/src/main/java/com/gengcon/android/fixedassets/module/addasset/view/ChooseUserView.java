package com.gengcon.android.fixedassets.module.addasset.view;

import com.gengcon.android.fixedassets.bean.result.ChooseUserBean;
import com.gengcon.android.fixedassets.module.base.Iview;

import java.util.List;

public interface ChooseUserView extends Iview {
    void showUser(List<ChooseUserBean> orgBeans);
}
