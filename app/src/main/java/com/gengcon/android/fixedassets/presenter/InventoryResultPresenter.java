package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InventoryR;
import com.gengcon.android.fixedassets.model.InventoryResultModel;
import com.gengcon.android.fixedassets.view.InventoryResultView;

public class InventoryResultPresenter extends BasePresenter<InventoryResultView> {

    private InventoryResultModel model;

    public InventoryResultPresenter() {
        this.model = new InventoryResultModel();
    }

    public void showInventoryResult(String inv_no, int status, final int page) {

        subscribe(model.showInventoryResult(inv_no, status, page), new ApiCallBack<Bean<InventoryR>>() {

            @Override
            public void onSuccess(Bean<InventoryR> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
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
                    if (page == 1) {
                        mMvpView.showLoading();
                    }
                }
            }
        });
    }
}
