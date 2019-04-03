package com.gengcon.android.fixedassets.module.inventory.presenter;

import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InventoryDetail;
import com.gengcon.android.fixedassets.bean.result.InventoryHeadDetail;
import com.gengcon.android.fixedassets.model.InventoryHeadModel;
import com.gengcon.android.fixedassets.model.InventoryListDetailModel;
import com.gengcon.android.fixedassets.module.inventory.view.InventoryDetailView;

public class InventoryDetailPresenter extends BasePresenter<InventoryDetailView> {

    private InventoryListDetailModel inventoryDetailModel;
    private InventoryHeadModel headModel;

    public InventoryDetailPresenter() {
        inventoryDetailModel = new InventoryListDetailModel();
        headModel = new InventoryHeadModel();
    }

    public void getInventoryDetail(String inv_no, final int page) {

        subscribe(inventoryDetailModel.getListDetail(inv_no, page), new ApiCallBack<Bean<InventoryDetail>>() {

            @Override
            public void onSuccess(Bean<InventoryDetail> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.initDetail(modelBean.getData());
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
                    if (page == 1)
                        mMvpView.hideLoading();
                }
            }

            @Override
            public void onStart() {
                if (isViewAttached()) {
                    if (page == 1)
                        mMvpView.showLoading();
                }
            }
        });
    }

    public void getInventoryHead(String inv_no) {

        subscribe(headModel.getHeadDetail(inv_no), new ApiCallBack<Bean<InventoryHeadDetail>>() {

            @Override
            public void onSuccess(Bean<InventoryHeadDetail> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.initHeadDetail(modelBean.getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {

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
