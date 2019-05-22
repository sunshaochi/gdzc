package com.gengcon.android.fixedassets.module.user.presenter;

import com.gengcon.android.fixedassets.common.module.http.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ContactUs;
import com.gengcon.android.fixedassets.model.ContactUsModel;
import com.gengcon.android.fixedassets.module.user.view.ContactUsView;

public class ContactUsPresenter extends BasePresenter<ContactUsView> {
    private ContactUsModel contactUsModel;

    public ContactUsPresenter() {
        this.contactUsModel = new ContactUsModel();
    }

    public void getContactUs() {
        subscribe(contactUsModel.getContactUs(), new ApiCallBack<Bean<ContactUs>>() {

            @Override
            public void onSuccess(Bean<ContactUs> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showData(modelBean.getData());
                        }
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        mMvpView.showInvalidType(modelBean.getData().getInvalid_type());
                    } else {
                        mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
//                if (isViewAttached()) {
//                    mMvpView.showErrorMsg(status, errorMsg);
//                }
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
