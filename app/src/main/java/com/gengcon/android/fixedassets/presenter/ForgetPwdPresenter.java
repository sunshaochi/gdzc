package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.model.ForgetPwdModel;
import com.gengcon.android.fixedassets.view.ForgetPwdView;

public class ForgetPwdPresenter extends BasePresenter<ForgetPwdView> {
    private ForgetPwdModel forgetPwdModel;

    public ForgetPwdPresenter() {
        this.forgetPwdModel = new ForgetPwdModel();
    }

    public void getForgetPwd(String secret_key, String new_pwd, String new_repwd) {
        subscribe(forgetPwdModel.getResetPwd(secret_key, new_pwd, new_repwd), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.setPwdSuccess();
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
