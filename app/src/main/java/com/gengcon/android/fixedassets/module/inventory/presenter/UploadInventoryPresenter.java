package com.gengcon.android.fixedassets.module.inventory.presenter;

import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.model.UploadInventoryModel;
import com.gengcon.android.fixedassets.module.inventory.view.UploadInventoryView;

import java.util.List;

public class UploadInventoryPresenter extends BasePresenter<UploadInventoryView> {

    private UploadInventoryModel uploadInventoryModel;

    public UploadInventoryPresenter() {
        uploadInventoryModel = new UploadInventoryModel();
    }

    public void uploadInventoryResult(String docId, List<String> asset_ids) {

        subscribe(uploadInventoryModel.uploadInventoryResult(docId, asset_ids), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.uploadResult(modelBean);
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
