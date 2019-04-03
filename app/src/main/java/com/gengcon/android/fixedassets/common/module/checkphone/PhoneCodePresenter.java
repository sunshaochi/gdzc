package com.gengcon.android.fixedassets.common.module.checkphone;

import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.model.PhoneCodeModel;

public class PhoneCodePresenter extends BasePresenter<PhoneCodeView> {
    private PhoneCodeModel phoneCodeModel;

    public PhoneCodePresenter() {
        this.phoneCodeModel = new PhoneCodeModel();
    }

    public void getPhoneCode(String phone, String type) {
        subscribe(phoneCodeModel.getPhoneCode(phone, type), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.showPhoneCode();
                    } else {
                        mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    mMvpView.showErrorMsg(status, errorMsg);
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
