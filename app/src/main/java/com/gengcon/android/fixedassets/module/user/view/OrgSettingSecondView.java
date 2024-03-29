package com.gengcon.android.fixedassets.module.user.view;

import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.module.base.Iview;

import java.util.List;

public interface OrgSettingSecondView extends Iview {
    void showOrg(List<OrgBean> orgBeans);

    void addOrg(String msg);

    void onFail(String msg);

    void editOrg(String msg);

    void delOrg(String msg);

    void getCodeSuc(String code);

    void getCodeFail();
}
