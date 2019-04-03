package com.gengcon.android.fixedassets.module.approval.presenter;

import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.ApprovalDetailBean;
import com.gengcon.android.fixedassets.bean.result.ApprovalHeadDetail;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InvalidBean;
import com.gengcon.android.fixedassets.model.ApprovalAssetListModel;
import com.gengcon.android.fixedassets.model.ApprovalHeadModel;
import com.gengcon.android.fixedassets.model.AuditSaveModel;
import com.gengcon.android.fixedassets.module.approval.view.ApprovalDetailView;
import com.google.gson.Gson;


public class ApprovalDetailPresenter extends BasePresenter<ApprovalDetailView> {

    private ApprovalHeadModel headModel;
    private ApprovalAssetListModel assetListModel;
    private AuditSaveModel auditSaveModel;

    public ApprovalDetailPresenter() {
        this.headModel = new ApprovalHeadModel();
        this.assetListModel = new ApprovalAssetListModel();
        this.auditSaveModel = new AuditSaveModel();
    }

    public void getApprovalDetail(String doc_no, final int page) {

        subscribe(assetListModel.getApprovalAssetList(doc_no, page), new ApiCallBack<Bean<ApprovalDetailBean>>() {

            @Override
            public void onSuccess(Bean<ApprovalDetailBean> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            if (page == 1) {
                                mMvpView.showApprovalDetail(modelBean.getData());
                            } else if (page > 1) {
                                mMvpView.showApprovalMoreDetail(modelBean.getData());
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
                if (isViewAttached()) {
                    mMvpView.showErrorMsg(status, errorMsg);
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

    public void getDetailHead(String doc_no) {

        subscribe(headModel.getHeadDetail(doc_no), new ApiCallBack<Bean<ApprovalHeadDetail>>() {

            @Override
            public void onSuccess(Bean<ApprovalHeadDetail> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showHeadDetail(modelBean.getData());
                        }
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

    public void getAuditSave(String doc_no, int result, String reason) {

        subscribe(auditSaveModel.getAuditSave(doc_no, result, reason), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.agreeApproval();
                    }  else if (modelBean.getCode().equals("CODE_401")) {
                        String json = modelBean.getData().toString();
                        Gson gson = new Gson();
                        InvalidBean invalidType = gson.fromJson(json, InvalidBean.class);
                        int invalid = invalidType.getInvalid_type();
                        mMvpView.showInvalidType(invalid);
                    }else {
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
