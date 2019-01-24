package com.gengcon.android.fixedassets.presenter;

import com.gengcon.android.fixedassets.base.ApiCallBack;
import com.gengcon.android.fixedassets.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.MessageBean;
import com.gengcon.android.fixedassets.model.UserNoticeModel;
import com.gengcon.android.fixedassets.view.MessageView;

public class MessagePresenter extends BasePresenter<MessageView> {
    private UserNoticeModel userNoticeModel;

    public MessagePresenter() {
        this.userNoticeModel = new UserNoticeModel();
    }

    public void getUserNotice(final int page) {
        subscribe(userNoticeModel.getUserNotice(page), new ApiCallBack<Bean<MessageBean>>() {

            @Override
            public void onSuccess(Bean<MessageBean> modelBean) {
                if (isViewAttached()) {
                    if (modelBean.getCode().equals("CODE_200")) {
                        if (modelBean.getData() != null) {
                            if (page == 1) {
                                mMvpView.showMessage(modelBean.getData());
                            } else {
                                mMvpView.showMoreMessage(modelBean.getData());
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

            }

            @Override
            public void onStart() {

            }
        });
    }
}
