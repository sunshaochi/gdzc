package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.model.ModifyPasswordModel;
import com.gengcon.android.fixedassets.view.ModifyPasswordView;


public class ModifyPasswordPresenter extends BasePresenter<ModifyPasswordView> {

    private ModifyPasswordModel modifyPasswordModel;

    public ModifyPasswordPresenter() {
        this.modifyPasswordModel = new ModifyPasswordModel();
    }

    public void getModifyPassword(String old_pwd, String new_pwd, String new_repwd) {

        subscribe(modifyPasswordModel.getModifyPassword(old_pwd, new_pwd, new_repwd), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.modifyPassword();
                    } else {
                        mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    mMvpView.modifyPasswordFail(errorMsg);
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
