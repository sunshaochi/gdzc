package com.gengcon.android.fixedassets.module.user.presenter;

import android.util.Log;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.DefaultBean;
import com.gengcon.android.fixedassets.bean.result.InvalidBean;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.common.module.http.ApiCallBack;
import com.gengcon.android.fixedassets.model.AddOrgModel;
import com.gengcon.android.fixedassets.model.DelOrgModel;
import com.gengcon.android.fixedassets.model.EditOrgModel;
import com.gengcon.android.fixedassets.model.OrgSettingListModel;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.module.user.view.OrgSettingSecondView;
import com.google.gson.Gson;

import java.util.List;

public class OrgSettingSecondPresenter extends BasePresenter<OrgSettingSecondView> {
    private OrgSettingListModel orgSettingListModel;
    private AddOrgModel orgModel;
    private EditOrgModel editOrgModel;
    private DelOrgModel delOrgModel;

    public OrgSettingSecondPresenter() {
        this.orgSettingListModel = new OrgSettingListModel();
        this.orgModel = new AddOrgModel();
        this.editOrgModel = new EditOrgModel();
        this.delOrgModel = new DelOrgModel();
    }

    public void getOrgSettingList(int pid) {
        subscribe(orgSettingListModel.getOrgSettingList(pid), new ApiCallBack<Bean<List<OrgBean>>>() {

            @Override
            public void onSuccess(Bean<List<OrgBean>> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showOrg(modelBean.getData());
                        }
                    } else {
                        mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    mMvpView.hideLoading();
                    if (status == 400) {
                        mMvpView.onFail(errorMsg);
                    } else if (status == 2 || status == 1 || status == 3 || status == 4) {
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

    public void addOrg(String json) {
        subscribe(orgModel.getAddOrg(json), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.addOrg(modelBean.getMsg());
                        }
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
                    if (status == 400) {
                        mMvpView.onFail(errorMsg);
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

    public void getDefaultCode(int type) {
        subscribe(orgModel.getDefaultcode(type), new ApiCallBack<Bean<DefaultBean>>() {
            @Override
            public void onSuccess(Bean<DefaultBean> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.getCodeSuc(modelBean.getData().getCode());
                        }
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
                    if (status == 400) {
                        mMvpView.onFail(errorMsg);
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


    public void editOrg(String json) {
         Log.e("请求参数", json );
         subscribe(editOrgModel.getEditOrg(json), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.editOrg(modelBean.getMsg());
                        }
                    } else if (modelBean.getCode().equals("CODE_401")) {
                        String json = modelBean.getData().toString();
                        Gson gson = new Gson();
                        InvalidBean invalidType = gson.fromJson(json, InvalidBean.class);
                        int invalid = invalidType.getInvalid_type();
                        mMvpView.showInvalidType(invalid);
                        ;
                    } else {
                        mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
                if (isViewAttached()) {
                    mMvpView.hideLoading();
                    if (status == 400) {
                        mMvpView.onFail(errorMsg);
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

    public void delOrg(String json) {
        subscribe(delOrgModel.getDelOrg(json), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.delOrg(modelBean.getMsg());
                        }
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
                    if (status == 400) {
                        mMvpView.onFail(errorMsg);
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
