package com.gengcon.android.fixedassets.module.addasset.presenter;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ClassificationBean;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.common.module.http.ApiCallBack;
import com.gengcon.android.fixedassets.model.AddAssetClassificationModel;
import com.gengcon.android.fixedassets.model.AddAssetOrgModel;
import com.gengcon.android.fixedassets.module.addasset.view.AddAssetDataView;
import com.gengcon.android.fixedassets.module.base.BasePresenter;

import java.util.List;

public class AddAssetDataPresenter extends BasePresenter<AddAssetDataView> {
    private AddAssetOrgModel addAssetOrgModel;
    private AddAssetClassificationModel addAssetClassificationModel;

    public AddAssetDataPresenter() {
        this.addAssetOrgModel = new AddAssetOrgModel();
        this.addAssetClassificationModel = new AddAssetClassificationModel();
    }

    public void getOrgList() {
        subscribe(addAssetOrgModel.getOrgList(), new ApiCallBack<Bean<List<OrgBean>>>() {

            @Override
            public void onSuccess(Bean<List<OrgBean>> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showOrg(modelBean.getData());
                        }
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        mMvpView.showInvalidType(2);
                    } else {
                        mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    mMvpView.showInvalidType(status);
                }
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onStart() {

            }
        });
    }

    public void getClassificationList() {
        subscribe(addAssetClassificationModel.getClassificationList(), new ApiCallBack<Bean<List<ClassificationBean>>>() {

            @Override
            public void onSuccess(Bean<List<ClassificationBean>> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showClassification(modelBean.getData());
                        }
                    } else {
                        mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
//                if (isViewAttached()) {
//                    mMvpView.showErrorMsg(status, errorMsg);
//                }
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onStart() {

            }
        });
    }
}
