package com.gengcon.android.fixedassets.module.addasset.presenter;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ChooseUserBean;
import com.gengcon.android.fixedassets.common.module.http.ApiCallBack;
import com.gengcon.android.fixedassets.model.ChooseUserModel;
import com.gengcon.android.fixedassets.module.addasset.view.ChooseUserView;
import com.gengcon.android.fixedassets.module.base.BasePresenter;

import java.util.List;

public class ChooseUserPresenter extends BasePresenter<ChooseUserView> {
    private ChooseUserModel chooseUserModel;

    public ChooseUserPresenter() {
        this.chooseUserModel = new ChooseUserModel();
    }

    public void getAssetUser(String wd, String org_id) {

        subscribe(chooseUserModel.getAssetUser(wd, org_id), new ApiCallBack<Bean<List<ChooseUserBean>>>() {

            @Override
            public void onSuccess(Bean<List<ChooseUserBean>> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showUser(modelBean.getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    if (status == 2 || status == 1 || status == 3 || status == 4) {
                        mMvpView.showInvalidType(status);
                    } else {
                        mMvpView.showErrorMsg(status, errorMsg);
                    }

                }
            }

            @Override
            public void onFinished() {
//                if (isViewAttached()) {
//                    mMvpView.hideLoading();
//                }
            }

            @Override
            public void onStart() {
//                if (isViewAttached()) {
//                    mMvpView.showLoading();
//                }
            }
        });
    }
}
