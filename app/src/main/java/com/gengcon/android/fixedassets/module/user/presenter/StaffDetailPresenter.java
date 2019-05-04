package com.gengcon.android.fixedassets.module.user.presenter;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.StaffDetailBean;
import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.model.DelEmpModel;
import com.gengcon.android.fixedassets.model.StaffDetailModel;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.module.user.view.StaffDetailView;

public class StaffDetailPresenter extends BasePresenter<StaffDetailView> {

    private StaffDetailModel staffDetailModel;
    private DelEmpModel delEmpModel;

    public StaffDetailPresenter() {
        this.staffDetailModel = new StaffDetailModel();
        this.delEmpModel = new DelEmpModel();
    }

    public void getStaffDetail(int emp_id) {
        subscribe(staffDetailModel.getStaffDetail(emp_id), new ApiCallBack<Bean<StaffDetailBean>>() {


            @Override
            public void onSuccess(Bean<StaffDetailBean> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.showStaffDetail(modelBean.getData());
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
                    mMvpView.hideLoading();
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

    public void getDelEmp(int emp_id) {
        subscribe(delEmpModel.getDelEmp(emp_id), new ApiCallBack<Bean>() {


            @Override
            public void onSuccess(Bean modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        mMvpView.showDelEmp();
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
                    mMvpView.hideLoading();
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
