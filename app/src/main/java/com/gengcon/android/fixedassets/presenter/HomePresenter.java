package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.Home;
import com.gengcon.android.fixedassets.bean.result.ResultRole;
import com.gengcon.android.fixedassets.model.CheckApiRouteModel;
import com.gengcon.android.fixedassets.model.HomeModel;
import com.gengcon.android.fixedassets.model.RouteModel;
import com.gengcon.android.fixedassets.view.HomeView;

import java.util.List;

public class HomePresenter extends BasePresenter<HomeView> {

    private HomeModel model;
    private CheckApiRouteModel checkApiRouteModel;
    private RouteModel routeModel;

    public HomePresenter() {
        this.model = new HomeModel();
        this.checkApiRouteModel = new CheckApiRouteModel();
        this.routeModel = new RouteModel();
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

    public void checkApiRoute(List<String> api_route) {
        subscribe(checkApiRouteModel.checkApiRoute(api_route), new ApiCallBack<Bean<List<Boolean>>>() {

            @Override
            public void onSuccess(Bean<List<Boolean>> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            List<Boolean> data = modelBean.getData();
                            mMvpView.checkApiRoute(data);
                        }
                    } else {
                        mMvpView.showCodeMsg(modelBean.getCode(), modelBean.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(int status, String errorMsg) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onStart() {

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


}
