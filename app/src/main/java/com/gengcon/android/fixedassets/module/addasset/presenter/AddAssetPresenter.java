package com.gengcon.android.fixedassets.module.addasset.presenter;

import android.text.TextUtils;

import com.gengcon.android.fixedassets.bean.Area;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.result.AddAssetCustom;
import com.gengcon.android.fixedassets.bean.result.AddAssetList;
import com.gengcon.android.fixedassets.bean.result.AssetCode;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.InvalidBean;
import com.gengcon.android.fixedassets.bean.result.UpLoadBean;
import com.gengcon.android.fixedassets.common.module.http.ApiCallBack;
import com.gengcon.android.fixedassets.model.AddAsseCustomModel;
import com.gengcon.android.fixedassets.model.AddAssetListModel;
import com.gengcon.android.fixedassets.model.AreaModel;
import com.gengcon.android.fixedassets.model.AssetAddModel;
import com.gengcon.android.fixedassets.model.AssetCodeModel;
import com.gengcon.android.fixedassets.model.UpLoadModel;
import com.gengcon.android.fixedassets.model.UsersModel;
import com.gengcon.android.fixedassets.module.addasset.view.AddAssetListView;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.util.Logger;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

public class AddAssetPresenter extends BasePresenter<AddAssetListView> {

    private AddAssetListModel assetListModel;
    private UsersModel usersModel;
    private AddAsseCustomModel asseCustomModel;
    private AssetCodeModel assetCodeModel;
    private AssetAddModel assetAddModel;
    private AreaModel areaModel;
    private UpLoadModel upLoadModel;


    public AddAssetPresenter() {
        this.usersModel = new UsersModel();
        this.assetListModel = new AddAssetListModel();
        this.asseCustomModel = new AddAsseCustomModel();
        this.assetCodeModel = new AssetCodeModel();
        this.assetAddModel = new AssetAddModel();
        this.areaModel = new AreaModel();
        this.upLoadModel = new UpLoadModel();
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
                if (isViewAttached()) {
                    if (status == 2 || status == 1 || status == 3 || status == 4) {
                        mMvpView.showInvalidType(status);
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

    public void getAddAsset(String json) {

        subscribe(assetAddModel.getAddAsset(json), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    mMvpView.hideLoading();
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
                    mMvpView.hideLoading();
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

    public void getArea() {

        subscribe(areaModel.getArea(), new ApiCallBack<Bean<List<Area>>>() {

            @Override
            public void onSuccess(Bean<List<Area>> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.initArea(modelBean.getData());
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

    public void upLoad(File file) {
        Logger.i("请求参数", file.toString());
        subscribe(upLoadModel.upLoadPhoto(file), new ApiCallBack<Bean<UpLoadBean>>() {
            @Override
            public void onSuccess(Bean<UpLoadBean> modelBean) {
                if (isViewAttached()) {
                    mMvpView.hideLoading();
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            if(!TextUtils.isEmpty(modelBean.getData().getPath())) {
                                mMvpView.upLoadingSuc(modelBean.getData().getPath());
                            }
                        }
                    } else {
                        mMvpView.upLoadingFai();
                        if (modelBean.getCode().equals("CODE_401")) {
                            String json = modelBean.getData().toString();
                            Gson gson = new Gson();
                            InvalidBean invalidBean = gson.fromJson(json, InvalidBean.class);
                            mMvpView.showInvalidType(invalidBean.getInvalid_type());
                        } else {
                            mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                        }
                    }

                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    mMvpView.hideLoading();
                    mMvpView.showErrorMsg(status, errorMsg);
                }
            }

            @Override
            public void onFinished() {

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
