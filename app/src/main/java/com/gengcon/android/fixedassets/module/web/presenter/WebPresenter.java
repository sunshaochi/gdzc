package com.gengcon.android.fixedassets.module.web.presenter;

import com.gengcon.android.fixedassets.common.module.htttp.ApiCallBack;
import com.gengcon.android.fixedassets.module.base.BasePresenter;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.model.PrintTagModel;


public class WebPresenter extends BasePresenter {

    private PrintTagModel printTagModel;

    public WebPresenter() {
        this.printTagModel = new PrintTagModel();
    }

    public void getPrintTag(String equipment_name) {
        subscribe(printTagModel.getPrintTag(equipment_name), new ApiCallBack<Bean>() {

            @Override
            public void onSuccess(Bean modelBean) {

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

}
