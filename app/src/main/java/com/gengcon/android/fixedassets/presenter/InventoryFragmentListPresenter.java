package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ResultInventorys;
import com.gengcon.android.fixedassets.model.DeleteInventoryModel;
import com.gengcon.android.fixedassets.model.MyCreatInventoryListModel;
import com.gengcon.android.fixedassets.model.MyTaskInventoryListModel;
import com.gengcon.android.fixedassets.ui.InventoryListActivity;
import com.gengcon.android.fixedassets.view.InventoryListView;

public class InventoryFragmentListPresenter extends BasePresenter<InventoryListView> {

    private MyTaskInventoryListModel myTaskInventoryListModel;
    private MyCreatInventoryListModel myCreatInventoryListModel;
    private DeleteInventoryModel deleteModel;

    public InventoryFragmentListPresenter() {
        this.myTaskInventoryListModel = new MyTaskInventoryListModel();
        this.myCreatInventoryListModel = new MyCreatInventoryListModel();
        this.deleteModel = new DeleteInventoryModel();
    }

    /**
     * tab = 1;我的任务
     * tab = 2;我创建的
     */

    public void getInventory(int page, int type) {
        if (type == 1) {
            subscribe(myTaskInventoryListModel.getMyTaskInventory(page), new ApiCallBack<Bean<ResultInventorys>>() {

                @Override
                public void onSuccess(Bean<ResultInventorys> modelBean) {
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
//                    mMvpView.hideLoading();
                }

                @Override
                public void onStart() {
//                    mMvpView.showLoading();
                }
            });
        } else {
            subscribe(myCreatInventoryListModel.getMyCreateInventory(page), new ApiCallBack<Bean<ResultInventorys>>() {

                @Override
                public void onSuccess(Bean<ResultInventorys> modelBean) {
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
//                    mMvpView.hideLoading();
                }

                @Override
                public void onStart() {
//                    mMvpView.showLoading();
                }
            });
        }
    }

    public void deleteInventory(String inv_no, final InventoryListActivity activity) {
        subscribe(deleteModel.deleteInventory(inv_no), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (modelBean.getCode().equals("CODE_200")) {
                    activity.initData();
                } else {
                    mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                mMvpView.showErrorMsg(status, errorMsg);
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
