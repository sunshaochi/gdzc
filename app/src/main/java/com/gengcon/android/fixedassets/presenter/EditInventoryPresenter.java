package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.result.InventoryDetail;
import com.gengcon.android.fixedassets.bean.result.InventoryHeadDetail;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.model.EditAssetListModel;
import com.gengcon.android.fixedassets.model.EditDetailModel;
import com.gengcon.android.fixedassets.model.InventoryHeadModel;
import com.gengcon.android.fixedassets.model.UpdateInventoryModel;
import com.gengcon.android.fixedassets.model.UsersModel;
import com.gengcon.android.fixedassets.view.EditInventoryView;

import java.util.List;

public class EditInventoryPresenter extends BasePresenter<EditInventoryView> {

    private UsersModel usersModel;
    private UpdateInventoryModel updateInventoryModel;
    private EditDetailModel editDetailModel;
    private InventoryHeadModel headModel;
    private EditAssetListModel editAssetListModel;

    public EditInventoryPresenter() {
        this.usersModel = new UsersModel();
        this.updateInventoryModel = new UpdateInventoryModel();
        this.editDetailModel = new EditDetailModel();
        this.headModel = new InventoryHeadModel();
        this.editAssetListModel = new EditAssetListModel();
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

    public void updateInventory(String inv_no, String inv_name, int userId, String remark, List<String> add_assetids, List<String> del_assetids) {

        subscribe(updateInventoryModel.updateInventory(inv_no, inv_name, userId, remark, add_assetids, del_assetids), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.updateInventoryResult(modelBean);
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    if (status == 402) {
                        mMvpView.contractExpire();
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

    public void getEditDeleteList(String inv_no, List<String> add_ids, List<String> del_ids, final int page) {

        subscribe(editAssetListModel.editAssetListDetail(inv_no, add_ids, del_ids, page), new ApiCallBack<Bean<InventoryDetail>>() {

            @Override
            public void onSuccess(Bean<InventoryDetail> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            if (page == 1) {
                                mMvpView.showInventoryResult(modelBean.getData());
                            } else if (page > 1) {
                                mMvpView.showInventoryMoreResult(modelBean.getData());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {

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

    public void getInventoryDetail(String inv_no, final int page) {

        subscribe(editDetailModel.getEditDetail(inv_no, page), new ApiCallBack<Bean<InventoryDetail>>() {

            @Override
            public void onSuccess(Bean<InventoryDetail> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            if (page == 1) {
                                mMvpView.showInventoryResult(modelBean.getData());
                            } else if (page > 1) {
                                mMvpView.showInventoryMoreResult(modelBean.getData());
                            }
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

    public void getInventoryHead(String inv_no) {

        subscribe(headModel.getHeadDetail(inv_no), new ApiCallBack<Bean<InventoryHeadDetail>>() {

            @Override
            public void onSuccess(Bean<InventoryHeadDetail> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.initHeadDetail(modelBean.getData());
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

            }

            @Override
            public void onStart() {

            }
        });
    }

}
