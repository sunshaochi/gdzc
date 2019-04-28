package com.gengcon.android.fixedassets.module.addasset.view;

import com.gengcon.android.fixedassets.bean.result.ClassificationBean;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.module.base.Iview;

import java.util.List;

public interface AddAssetDataView extends Iview {
    void showOrg(List<OrgBean> orgBeans);
    void showClassification(List<ClassificationBean> classificationBeans);
}
