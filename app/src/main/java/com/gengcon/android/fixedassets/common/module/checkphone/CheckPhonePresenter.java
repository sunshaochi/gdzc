package com.gengcon.android.fixedassets.common.module.checkphone;


import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ForgetPwd;
import com.gengcon.android.fixedassets.bean.result.InvalidBean;
import com.gengcon.android.fixedassets.model.CheckNewPhoneModel;
import com.gengcon.android.fixedassets.model.CheckOldPhoneModel;
import com.gengcon.android.fixedassets.model.ForgetPhoneModel;
import com.gengcon.android.fixedassets.model.RegisterPhoneModel;
import com.google.gson.Gson;

public class CheckPhonePresenter extends BasePresenter<CheckPhoneCodeView> {
    private CheckOldPhoneModel checkOldPhoneModel;
    private CheckNewPhoneModel checkNewPhoneModel;
    private RegisterPhoneModel registerPhoneModel;
    private ForgetPhoneModel forgetPhoneModel;

    public CheckPhonePresenter() {
        this.checkOldPhoneModel = new CheckOldPhoneModel();
        this.checkNewPhoneModel = new CheckNewPhoneModel();
        this.registerPhoneModel = new RegisterPhoneModel();
        this.forgetPhoneModel = new ForgetPhoneModel();
    }

    public void getCheckOldPhone(String phone, String code) {
        subscribe(checkOldPhoneModel.getCheckOldPhone(phone, code), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.checkPhoneCode();
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        String json = modelBean.getData().toString();
                        Gson gson = new Gson();
                        InvalidBean invalidType = gson.fromJson(json, InvalidBean.class);
                        int invalid = invalidType.getInvalid_type();
                        mMvpView.showInvalidType(invalid);
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

    public void getCheckNewPhone(String phone, String code) {
        subscribe(checkNewPhoneModel.getCheckNewPhone(phone, code), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.checkPhoneCode();
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        String json = modelBean.getData().toString();
                        Gson gson = new Gson();
                        InvalidBean invalidType = gson.fromJson(json, InvalidBean.class);
                        int invalid = invalidType.getInvalid_type();
                        mMvpView.showInvalidType(invalid);
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

    public void getRegisterPhone(String phone, String code) {
        subscribe(registerPhoneModel.getRegisterPhone(phone, code), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.checkPhoneCode();
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

    public void getResetPwdVerify(String phone, String code) {
        subscribe(forgetPhoneModel.getResetPwdVerify(phone, code), new ApiCallBack<Bean<ForgetPwd>>() {

            @Override
            public void onSuccess(Bean<ForgetPwd> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.checkForgetPhoneCode(modelBean.getData());
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
