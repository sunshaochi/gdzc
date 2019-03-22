package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultApprovals;
import com.gengcon.android.fixedassets.model.CompleteApprovalListModel;
import com.gengcon.android.fixedassets.model.WaitApprovalListModel;
import com.gengcon.android.fixedassets.view.ApprovalListView;

public class ApprovalFragmentListPresenter extends BasePresenter<ApprovalListView> {

    private WaitApprovalListModel waitApprovalListModel;
    private CompleteApprovalListModel completeApprovalListModel;

    public ApprovalFragmentListPresenter() {
        this.waitApprovalListModel = new WaitApprovalListModel();
        this.completeApprovalListModel = new CompleteApprovalListModel();
    }

    /**
     * type = 1;待审批
     * type = 2;已审批
     */

    public void getApprovalList(int page, int type) {
        if (type == 1) {
            subscribe(waitApprovalListModel.getWaitApprovalList(page), new ApiCallBack<Bean<ResultApprovals>>() {

                @Override
                public void onSuccess(Bean<ResultApprovals> modelBean) {
                    if (isViewAttached()) {
                        if (modelBean.getCode().equals("CODE_200")) {
                            if (modelBean.getData() != null) {
                                mMvpView.showApprovalList(modelBean.getData());
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
                    if (isViewAttached()) {
                        mMvpView.showErrorMsg(status, errorMsg);
                    }
                }

                @Override
                public void onFinished() {
//                    mMvpView.hideLoading();
                }

                @Override
                public void onStart() {
//                    mMvpView.showLoading();
                }
            });
        } else {
            subscribe(completeApprovalListModel.getCompleteApprovalList(page), new ApiCallBack<Bean<ResultApprovals>>() {

                @Override
                public void onSuccess(Bean<ResultApprovals> modelBean) {
                    if (isViewAttached()) {
                        if (modelBean.getCode().equals("CODE_200")) {
                            if (modelBean.getData() != null) {
                                mMvpView.showApprovalList(modelBean.getData());
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
                    if (isViewAttached()) {
                        mMvpView.showErrorMsg(status, errorMsg);
                    }
                }

                @Override
                public void onFinished() {
//                    mMvpView.hideLoading();
                }

                @Override
                public void onStart() {
//                    mMvpView.showLoading();
                }
            });
        }
    }
}
