package com.gengcon.android.fixedassets.module.inventory.presenter;

import com.gengcon.android.fixedassets.bean.result.InvalidBean;
import com.gengcon.android.fixedassets.bean.result.ResultAsset;
import com.gengcon.android.fixedassets.bean.result.SyncDataFailBean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.model.AssetOffNetListModel;
import com.gengcon.android.fixedassets.model.AssetSyncDataModel;
import com.gengcon.android.fixedassets.model.SonAuditModel;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.module.inventory.view.InventoryResultView;
import com.google.gson.Gson;

import java.util.List;

public class InventoryResultPresenter extends BasePresenter<InventoryResultView> {

    private AssetOffNetListModel assetOffNetListModel;
    private AssetSyncDataModel assetSyncDataModel;
    private SonAuditModel sonAuditModel;
    private int pageCount;

    public InventoryResultPresenter() {
        this.assetOffNetListModel = new AssetOffNetListModel();
        this.assetSyncDataModel = new AssetSyncDataModel();
        this.sonAuditModel = new SonAuditModel();
    }

    public void showInventoryResult(String pd_no, final int page) {

        subscribe(assetOffNetListModel.getAssetOffNetList(pd_no, page), new ApiCallBack<Bean<ResultAsset>>() {

            @Override
            public void onSuccess(Bean<ResultAsset> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            pageCount = modelBean.getData().getPage_count();
                            mMvpView.showInventoryResult(modelBean.getData());
                        }
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        mMvpView.showInvalidType(modelBean.getData().getInvalid_type());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    mMvpView.hideLoading();
                    mMvpView.showErrorMsg(status, errorMsg);
                }
            }

            @Override
            public void onFinished() {
                if (isViewAttached()) {
                    if (page == pageCount) {
                        mMvpView.hideLoading();
                    }
                }
            }

            @Override
            public void onStart() {
                if (isViewAttached()) {
                    if (page == 1) {
                        mMvpView.showLoading();
                    }
                }
            }
        });
    }

    public void auditAssetData(String pd_no, String remarks, final List<String> asset_ids) {

        subscribe(sonAuditModel.getSonAudit(pd_no, remarks, asset_ids), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.keepSonAuditSuccess();
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        String json = modelBean.getData().toString();
                        Gson gson = new Gson();
                        InvalidBean invalidType = gson.fromJson(json, InvalidBean.class);
                        int invalid = invalidType.getInvalid_type();
                        mMvpView.showInvalidType(invalid);
                    } else if (modelBean.getCode().equals("CODE_400")) {
                        String json = modelBean.getData().toString();
                        Gson gson = new Gson();
                        SyncDataFailBean syncDataFailBean = gson.fromJson(json, SyncDataFailBean.class);
                        int type = syncDataFailBean.getType();
                        mMvpView.keepSonAuditFailed(type, modelBean.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    mMvpView.hideLoading();
                    mMvpView.showErrorMsg(status, errorMsg);
                }
            }

            @Override
            public void onFinished() {
                if (isViewAttached()) {
                    mMvpView.hideLoading();
                }
            }

            @Override
            public void onStart() {
                if (isViewAttached()) {
                    mMvpView.showLoading();
                }
            }
        });
    }

    public void showSyncAssetData(String pd_no, final List<String> asset_ids) {

        subscribe(assetSyncDataModel.getAssetSyncData(pd_no, asset_ids), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.syncAssetSuccess();
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        String json = modelBean.getData().toString();
                        Gson gson = new Gson();
                        InvalidBean invalidType = gson.fromJson(json, InvalidBean.class);
                        int invalid = invalidType.getInvalid_type();
                        mMvpView.showInvalidType(invalid);
                    } else if (modelBean.getCode().equals("CODE_400")) {
                        String json = modelBean.getData().toString();
                        Gson gson = new Gson();
                        SyncDataFailBean syncDataFailBean = gson.fromJson(json, SyncDataFailBean.class);
                        int type = syncDataFailBean.getType();
                        mMvpView.syncAssetFailed(type, modelBean.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    mMvpView.hideLoading();
                    mMvpView.showErrorMsg(status, errorMsg);
                }
            }

            @Override
            public void onFinished() {
                if (isViewAttached()) {
                    mMvpView.hideLoading();
                }
            }

            @Override
            public void onStart() {
                if (isViewAttached()) {
                    mMvpView.showLoading();
                }
            }
        });
    }
}
