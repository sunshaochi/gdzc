package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.model.CheckNewPhoneModel;
import com.gengcon.android.fixedassets.model.CheckOldPhoneModel;
import com.gengcon.android.fixedassets.view.CheckOldPhoneView;

public class CheckPhonePresenter extends BasePresenter<CheckOldPhoneView> {
    private CheckOldPhoneModel checkOldPhoneModel;
    private CheckNewPhoneModel checkNewPhoneModel;

    public CheckPhonePresenter() {
        this.checkOldPhoneModel = new CheckOldPhoneModel();
        this.checkNewPhoneModel = new CheckNewPhoneModel();
    }

    public void getCheckOldPhone(String phone, String code) {
        subscribe(checkOldPhoneModel.getCheckOldPhone(phone, code), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.checkPhone();
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    mMvpView.showErrorMsg(status, errorMsg);
                    mMvpView.checkPhoneFail(errorMsg);
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

    public void getCheckNewPhone(String phone, String code) {
        subscribe(checkNewPhoneModel.getCheckNewPhone(phone, code), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.checkPhone();
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    mMvpView.showErrorMsg(status, errorMsg);
                    mMvpView.checkPhoneFail(errorMsg);
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
}
