package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultInventorysNum;
import com.gengcon.android.fixedassets.model.InventoryNumModel;
import com.gengcon.android.fixedassets.view.InventoryNumView;

public class InventoryListPresenter extends BasePresenter<InventoryNumView> {

    private InventoryNumModel model;

    public InventoryListPresenter() {
        this.model = new InventoryNumModel();
    }

    public void getInventorysNum() {

        subscribe(model.getInventorysNum(), new ApiCallBack<Bean<ResultInventorysNum>>() {

            @Override
            public void onSuccess(Bean<ResultInventorysNum> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showInventorys(modelBean.getData());
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
//                mMvpView.hideLoading();
            }

            @Override
            public void onStart() {
//                mMvpView.showLoading();
            }
        });
    }
}
