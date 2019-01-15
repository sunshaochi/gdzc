package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.Home;
import com.gengcon.android.fixedassets.model.ScanLoginMode;
import com.gengcon.android.fixedassets.view.ScanLoginView;

public class ScanLoginPresenter extends BasePresenter<ScanLoginView> {

    private ScanLoginMode model;
//    private ScanResultModel resultModel;

    public ScanLoginPresenter() {
        this.model = new ScanLoginMode();
//        this.resultModel = new ScanResultModel();
    }

//    public void scanResult(String uuid, String scanResult) {
//        subscribe(resultModel.scanCodeResult(uuid, scanResult), new ApiCallBack() {
//            @Override
//            public void onSuccess(Object modelBean) {
//
//            }
//
//            @Override
//            public void onFailure(int status, String errorMsg) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        });
//    }

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
