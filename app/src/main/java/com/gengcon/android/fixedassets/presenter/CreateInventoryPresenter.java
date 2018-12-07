package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.model.CreateInventoryModel;
import com.gengcon.android.fixedassets.model.UsersModel;
import com.gengcon.android.fixedassets.view.CreateInventoryView;

import java.util.List;

public class CreateInventoryPresenter extends BasePresenter<CreateInventoryView> {

    private UsersModel usersModel;
    private CreateInventoryModel inventoryModel;

    public CreateInventoryPresenter() {
        this.usersModel = new UsersModel();
        inventoryModel = new CreateInventoryModel();
    }

    public void getUsers() {

        subscribe(usersModel.getUsers(), new ApiCallBack<Bean<List<User>>>() {

            @Override
            public void onSuccess(Bean<List<User>> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.initUsers(modelBean.getData());
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

    public void addInventory(String docName, int userId, String remark, List<String> asset_ids) {

        subscribe(inventoryModel.addInventory(docName, userId, remark, asset_ids), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.addInventoryResult(modelBean);
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
