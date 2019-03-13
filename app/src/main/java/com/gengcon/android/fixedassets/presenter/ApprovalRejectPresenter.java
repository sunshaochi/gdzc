package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.model.AuditSaveModel;
import com.gengcon.android.fixedassets.view.ApprovalRejectView;


public class ApprovalRejectPresenter extends BasePresenter<ApprovalRejectView> {

    private AuditSaveModel auditSaveModel;

    public ApprovalRejectPresenter() {
        this.auditSaveModel = new AuditSaveModel();
    }

    public void getAuditSave(String doc_no, int result, String reason) {

        subscribe(auditSaveModel.getAuditSave(doc_no, result, reason), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.rejectApproval();
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