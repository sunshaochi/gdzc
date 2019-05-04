package com.gengcon.android.fixedassets.module.user.presenter;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InvalidBean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.model.AddEmpModel;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.module.user.view.AddEmpView;
import com.google.gson.Gson;

public class AddEmpPresenter extends BasePresenter<AddEmpView> {

    private AddEmpModel addEmpModel;

    public AddEmpPresenter() {
        this.addEmpModel = new AddEmpModel();
    }

    public void getAddEmp(String json) {
        subscribe(addEmpModel.getAddEmp(json), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.addEmpSuccess();
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
                    mMvpView.hideLoading();
                    if (status == 400) {
                        mMvpView.addEmpFail(errorMsg);
                    } else {
                        mMvpView.showErrorMsg(status, errorMsg);
                    }
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
