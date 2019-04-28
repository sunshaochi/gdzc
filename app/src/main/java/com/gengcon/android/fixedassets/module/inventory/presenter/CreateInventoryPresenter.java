package com.gengcon.android.fixedassets.module.inventory.presenter;

import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InvalidBean;
import com.gengcon.android.fixedassets.model.CreateInventoryModel;
import com.gengcon.android.fixedassets.model.UsersModel;
import com.gengcon.android.fixedassets.module.inventory.view.CreateInventoryView;
import com.google.gson.Gson;

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
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        mMvpView.showInvalidType(2);
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

    public void addInventory(String docName, int userId, String remark, List<String> asset_ids) {

        subscribe(inventoryModel.addInventory(docName, userId, remark, asset_ids), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.addInventoryResult(modelBean);
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        String json = modelBean.getData().toString();
                        Gson gson = new Gson();
                        InvalidBean invalidType = gson.fromJson(json, InvalidBean.class);
                        int invalid = invalidType.getInvalid_type();
                        mMvpView.showInvalidType(invalid);
                    } else if (modelBean.getCode().equals("CODE_402")) {
                        mMvpView.contractExpire();
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