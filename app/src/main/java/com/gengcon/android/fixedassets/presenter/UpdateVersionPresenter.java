package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.UpdateVersion;
import com.gengcon.android.fixedassets.model.GetVersionModel;
import com.gengcon.android.fixedassets.view.UpdateVersionView;

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
                    } else {
                        mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
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
