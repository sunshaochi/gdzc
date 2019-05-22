package com.gengcon.android.fixedassets.module.user.presenter;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.StaffManagerBean;
import com.gengcon.android.fixedassets.common.module.http.ApiCallBack;
import com.gengcon.android.fixedassets.model.StaffManagerListModel;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.module.user.view.StaffManagerView;

public class StaffManagerPresenter extends BasePresenter<StaffManagerView> {
    private StaffManagerListModel staffManagerListModel;

    public StaffManagerPresenter() {
        this.staffManagerListModel = new StaffManagerListModel();
    }

    public void getStaffManagerList(int pid) {
        subscribe(staffManagerListModel.getStaffManagerList(pid), new ApiCallBack<Bean<StaffManagerBean>>() {

            @Override
            public void onSuccess(Bean<StaffManagerBean> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            mMvpView.showStaff(modelBean.getData());
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
