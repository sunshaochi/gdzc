package com.gengcon.android.fixedassets.module.approval.presenter;

import com.gengcon.android.fixedassets.common.module.http.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.ApprovalNum;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.model.ApprovalNumModel;
import com.gengcon.android.fixedassets.module.approval.view.ApprovalNumView;

public class ApprovalListPresenter extends BasePresenter<ApprovalNumView> {

    private ApprovalNumModel model;

    public ApprovalListPresenter() {
        this.model = new ApprovalNumModel();
    }

    public void getApprovalNum() {

        subscribe(model.getApprovalNum(), new ApiCallBack<Bean<ApprovalNum>>() {

            @Override
            public void onSuccess(Bean<ApprovalNum> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showApproval(modelBean.getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                mMvpView.showErrorMsg(status, errorMsg);
            }

            @Override
            public void onFinished() {
//                mMvpView.hideLoading();
            }

            @Override
            public void onStart() {
//                mMvpView.showLoading();
            }
        });
    }
}
