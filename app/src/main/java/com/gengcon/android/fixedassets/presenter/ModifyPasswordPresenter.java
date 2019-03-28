package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InvalidBean;
import com.gengcon.android.fixedassets.model.ModifyPasswordModel;
import com.gengcon.android.fixedassets.view.ModifyPasswordView;
import com.google.gson.Gson;


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
                if (isViewAttached()){
                    mMvpView.hideLoading();
                }
            }

            @Override
            public void onStart() {
                if (isViewAttached()){
                    mMvpView.showLoading();
                }
            }
        });
    }

}
