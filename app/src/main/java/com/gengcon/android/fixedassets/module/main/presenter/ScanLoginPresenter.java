package com.gengcon.android.fixedassets.module.main.presenter;

import com.gengcon.android.fixedassets.common.module.http.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.Home;
import com.gengcon.android.fixedassets.model.ScanLoginMode;
import com.gengcon.android.fixedassets.module.main.view.ScanLoginView;

public class ScanLoginPresenter extends BasePresenter<ScanLoginView> {

    private ScanLoginMode model;

    public ScanLoginPresenter() {
        this.model = new ScanLoginMode();
    }

    public void scanLogin(String uuid, int login_status) {

        subscribe(model.scanLogin(uuid, login_status), new ApiCallBack<Bean<Home>>() {

            @Override
            public void onSuccess(Bean<Home> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.showScanResult(modelBean);
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {

            }

            @Override
            public void onFinished() {
                if (isViewAttached()) {
                    mMvpView.hideLoading();
                }
            }

            @Override
            public void onStart() {
                if (isViewAttached()) {
                    mMvpView.showLoading();
                }
            }
        });
    }
}
