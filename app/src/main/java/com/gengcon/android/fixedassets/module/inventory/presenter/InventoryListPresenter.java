package com.gengcon.android.fixedassets.module.inventory.presenter;

import com.gengcon.android.fixedassets.bean.result.ResultInventory;
import com.gengcon.android.fixedassets.common.module.http.ApiCallBack;
import com.gengcon.android.fixedassets.model.OffnetListModel;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.module.inventory.view.InventoryListView;

public class InventoryListPresenter extends BasePresenter<InventoryListView> {

    private OffnetListModel offnetListModel;

    public InventoryListPresenter() {
        this.offnetListModel = new OffnetListModel();
    }

    public void getOffnetList() {

        subscribe(offnetListModel.getOffnetList(), new ApiCallBack<Bean<ResultInventory>>() {

            @Override
            public void onSuccess(Bean<ResultInventory> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showOffnetList(modelBean.getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {

            }

            @Override
            public void onFinished() {
//                mMvpView.hideLoading();
            }

            @Override
            public void onStart() {
//                mMvpView.showLoading();
            }
        });
    }
}
