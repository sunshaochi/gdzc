package com.gengcon.android.fixedassets.common.module.update;

import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.UpdateVersion;
import com.gengcon.android.fixedassets.model.GetVersionModel;

public class UpdateVersionPresenter extends BasePresenter<UpdateVersionView> {

    private GetVersionModel model;

    public UpdateVersionPresenter() {
        this.model = new GetVersionModel();
    }

    public void getVersion() {

        subscribe(model.getVersion(), new ApiCallBack<Bean<UpdateVersion>>() {

            @Override
            public void onSuccess(Bean<UpdateVersion> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.updateVersion(modelBean.getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
//                if (isViewAttached()) {
////                    mMvpView.showErrorMsg(status, errorMsg);
//                }
            }

            @Override
            public void onFinished() {
//                if (isViewAttached()) {
//                    mMvpView.hideLoading();
//                }
            }

            @Override
            public void onStart() {
//                if (isViewAttached()) {
//                    mMvpView.showLoading();
//                }
            }
        });
    }
}
