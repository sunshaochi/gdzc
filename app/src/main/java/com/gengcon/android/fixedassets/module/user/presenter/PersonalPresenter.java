package com.gengcon.android.fixedassets.module.user.presenter;

import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.PersonalBean;
import com.gengcon.android.fixedassets.model.PersonalModel;
import com.gengcon.android.fixedassets.module.user.view.PersonalView;

public class PersonalPresenter extends BasePresenter<PersonalView> {

    private PersonalModel personalModel;


    public PersonalPresenter() {
        this.personalModel = new PersonalModel();
    }

    public void getUserData() {
        subscribe(personalModel.getPersonal(), new ApiCallBack<Bean<PersonalBean>>() {


            @Override
            public void onSuccess(Bean<PersonalBean> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.showPersonalData(modelBean.getData());
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        mMvpView.showInvalidType(modelBean.getData().getInvalid_type());
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
