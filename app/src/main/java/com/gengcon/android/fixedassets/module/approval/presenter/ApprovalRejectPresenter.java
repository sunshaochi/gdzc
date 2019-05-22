package com.gengcon.android.fixedassets.module.approval.presenter;

import com.gengcon.android.fixedassets.common.module.http.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InvalidBean;
import com.gengcon.android.fixedassets.model.AuditSaveModel;
import com.gengcon.android.fixedassets.module.approval.view.ApprovalRejectView;
import com.google.gson.Gson;


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
                    if (status == 402) {
                        mMvpView.contractExpire();
                    } else {
                        mMvpView.showErrorMsg(status, errorMsg);
                    }
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
