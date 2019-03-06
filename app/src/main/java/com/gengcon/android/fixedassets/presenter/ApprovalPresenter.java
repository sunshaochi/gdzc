package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.ApprovalListBean;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.model.ApprovalListModel;
import com.gengcon.android.fixedassets.view.ApprovalView;

public class ApprovalPresenter extends BasePresenter<ApprovalView> {
    private ApprovalListModel approvalListModel;

    public ApprovalPresenter() {
        this.approvalListModel = new ApprovalListModel();
    }

    public void getApprovalList(final int page) {
        subscribe(approvalListModel.getApprovalList(page), new ApiCallBack<Bean<ApprovalListBean>>() {

            @Override
            public void onSuccess(Bean<ApprovalListBean> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            if (page == 1) {
                                mMvpView.showApproval(modelBean.getData());
                            } else {
                                mMvpView.showMoreApproval(modelBean.getData());
                            }
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
