package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.PersonalBean;
import com.gengcon.android.fixedassets.model.PersonalModel;
import com.gengcon.android.fixedassets.view.PersonalView;

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
                    } else {
                        mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    mMvpView.showPersonalDataFail(errorMsg);
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
