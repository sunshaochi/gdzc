package com.gengcon.android.fixedassets.module.inventory.presenter;

import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.PreviewInfo;
import com.gengcon.android.fixedassets.model.PreviewInfoModel;
import com.gengcon.android.fixedassets.module.inventory.view.PreviewInfoView;

import java.util.List;

public class PreviewInfoPresenter extends BasePresenter<PreviewInfoView> {

    private PreviewInfoModel model;

    public PreviewInfoPresenter() {
        this.model = new PreviewInfoModel();
    }

    public void previewInfo(String inv_no, int status, int page, List<String> assetIds) {

        subscribe(model.previewInventoryInfo(inv_no, assetIds, status, page), new ApiCallBack<Bean<PreviewInfo>>() {

            @Override
            public void onSuccess(Bean<PreviewInfo> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showPreviewInfo(modelBean.getData());
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