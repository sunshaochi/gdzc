package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.Industry;
import com.gengcon.android.fixedassets.bean.result.UserData;
import com.gengcon.android.fixedassets.model.CheckRenameModel;
import com.gengcon.android.fixedassets.model.IndustryModel;
import com.gengcon.android.fixedassets.model.RegisterModel;
import com.gengcon.android.fixedassets.view.RegisterLastView;

import java.util.List;

public class RegisterLastPresenter extends BasePresenter<RegisterLastView> {
    private IndustryModel industryModel;
    private CheckRenameModel checkRenameModel;
    private RegisterModel registerModel;

    public RegisterLastPresenter() {
        this.industryModel = new IndustryModel();
        this.checkRenameModel = new CheckRenameModel();
        this.registerModel = new RegisterModel();
    }

    public void getIndustry() {
        subscribe(industryModel.getIndustry(), new ApiCallBack<Bean<List<Industry>>>() {

            @Override
            public void onSuccess(Bean<List<Industry>> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showIndustry(modelBean.getData());
                        }
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

    public void getCheckRename(String user) {
        subscribe(checkRenameModel.getCheckRename(user), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.checkRename();
                    } else {

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

    public void getRegister(String user, String pwd, String phone, String company_name, int ind_id) {
        subscribe(registerModel.getRegister(user, pwd, phone, company_name, ind_id), new ApiCallBack<Bean<UserData>>() {

            @Override
            public void onSuccess(Bean<UserData> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.completeRegister(modelBean.getData());
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
