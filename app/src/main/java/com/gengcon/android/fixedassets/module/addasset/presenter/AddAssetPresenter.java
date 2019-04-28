package com.gengcon.android.fixedassets.module.addasset.presenter;

import com.gengcon.android.fixedassets.bean.Area;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.result.AddAssetCustom;
import com.gengcon.android.fixedassets.bean.result.AddAssetList;
import com.gengcon.android.fixedassets.bean.result.AssetCode;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InvalidBean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.model.AddAsseCustomModel;
import com.gengcon.android.fixedassets.model.AddAssetListModel;
import com.gengcon.android.fixedassets.model.AreaModel;
import com.gengcon.android.fixedassets.model.AssetAddModel;
import com.gengcon.android.fixedassets.model.AssetCodeModel;
import com.gengcon.android.fixedassets.model.UsersModel;
import com.gengcon.android.fixedassets.module.addasset.view.AddAssetListView;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.google.gson.Gson;

import java.util.List;

public class AddAssetPresenter extends BasePresenter<AddAssetListView> {

    private AddAssetListModel assetListModel;
    private UsersModel usersModel;
    private AddAsseCustomModel asseCustomModel;
    private AssetCodeModel assetCodeModel;
    private AssetAddModel assetAddModel;
    private AreaModel areaModel;


    public AddAssetPresenter() {
        this.usersModel = new UsersModel();
        this.assetListModel = new AddAssetListModel();
        this.asseCustomModel = new AddAsseCustomModel();
        this.assetCodeModel = new AssetCodeModel();
        this.assetAddModel = new AssetAddModel();
        this.areaModel = new AreaModel();
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

    public void getAssetCode() {

        subscribe(assetCodeModel.getAssetCode(), new ApiCallBack<AssetCode>() {

            @Override
            public void onSuccess(AssetCode modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showAssetCode(modelBean.getData());
                        }
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

    public void getAddAssetList() {

        subscribe(assetListModel.getAddAssetList(), new ApiCallBack<Bean<AddAssetList>>() {

            @Override
            public void onSuccess(Bean<AddAssetList> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showAddAssetList(modelBean.getData());
                        }
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        mMvpView.showInvalidType(modelBean.getData().getInvalid_type());
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

    public void getAddAssetCustom(String tpl_id) {

        subscribe(asseCustomModel.getAddAssetCustom(tpl_id), new ApiCallBack<Bean<List<AddAssetCustom>>>() {

            @Override
            public void onSuccess(Bean<List<AddAssetCustom>> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showAddAssetCustom(modelBean.getData());
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

    public void getAddAsset(String json) {

        subscribe(assetAddModel.getAddAsset(json), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.addAsset();
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        String json = modelBean.getData().toString();
                        Gson gson = new Gson();
                        InvalidBean invalidType = gson.fromJson(json, InvalidBean.class);
                        int invalid = invalidType.getInvalid_type();
                        mMvpView.showInvalidType(invalid);
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
//                mMvpView.hideLoading();
            }

            @Override
            public void onStart() {
//                mMvpView.showLoading();
            }
        });
    }

    public void getArea() {

        subscribe(areaModel.getArea(), new ApiCallBack<Bean<List<Area>>>() {

            @Override
            public void onSuccess(Bean<List<Area>> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.initArea(modelBean.getData());
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

}