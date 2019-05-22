package com.gengcon.android.fixedassets.module.main.presenter;

import com.gengcon.android.fixedassets.common.module.http.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.Home;
import com.gengcon.android.fixedassets.bean.result.ResultRole;
import com.gengcon.android.fixedassets.bean.result.UserPopupNotice;
import com.gengcon.android.fixedassets.model.HomeModel;
import com.gengcon.android.fixedassets.model.RouteModel;
import com.gengcon.android.fixedassets.model.UserPopupNoticeModel;
import com.gengcon.android.fixedassets.module.main.view.HomeView;


public class HomePresenter extends BasePresenter<HomeView> {

    private HomeModel model;
    private RouteModel routeModel;
    private UserPopupNoticeModel userPopupNoticeModel;

    public HomePresenter() {
        this.model = new HomeModel();
        this.routeModel = new RouteModel();
        this.userPopupNoticeModel = new UserPopupNoticeModel();
    }

    public void getHome() {

        subscribe(model.getHome(), new ApiCallBack<Bean<Home>>() {

            @Override
            public void onSuccess(Bean<Home> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showHome(modelBean.getData());
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
//                if (isViewAttached()) {
//                    mMvpView.showErrorMsg(status, errorMsg);
//                }
            }

            @Override
            public void onFinished() {
                if (isViewAttached()) {
//                    mMvpView.hideLoading();
                }
            }

            @Override
            public void onStart() {
                if (isViewAttached()) {
//                    mMvpView.showLoading();
                }
            }
        });
    }

    public void getRoute() {

        subscribe(routeModel.getApiRoute(), new ApiCallBack<Bean<ResultRole>>() {

            @Override
            public void onSuccess(Bean<ResultRole> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showApiRoute(modelBean.getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {

            }

            @Override
            public void onFinished() {
                if (isViewAttached()) {
//                    mMvpView.hideLoading();
                }
            }

            @Override
            public void onStart() {
                if (isViewAttached()) {
//                    mMvpView.showLoading();
                }
            }
        });
    }

    public void getUserNotice() {
        subscribe(userPopupNoticeModel.getUserPopupNotice(), new ApiCallBack<Bean<UserPopupNotice>>() {

            @Override
            public void onSuccess(Bean<UserPopupNotice> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showNotice(modelBean.getData());
                        }
                    } else {
                        mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {
//                if (isViewAttached()) {
//                    mMvpView.showErrorMsg(status, errorMsg);
//                }
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onStart() {

            }
        });
    }

//    public void getReadAddNotice(int notice_id) {
//        subscribe(addNoticeModel.getReadAddNotice(notice_id), new ApiCallBack<Bean>() {
//
//            @Override
//            public void onSuccess(Bean modelBean) {
//
//            }
//
//            @Override
//            public void onFailure(int status, String errorMsg) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        });
//    }

//    public void getReadEditNotice(int notice_id) {
//        subscribe(editNoticeModel.getReadEditNotice(notice_id), new ApiCallBack<Bean>() {
//
//            @Override
//            public void onSuccess(Bean modelBean) {
//
//            }
//
//            @Override
//            public void onFailure(int status, String errorMsg) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        });
//    }

}
