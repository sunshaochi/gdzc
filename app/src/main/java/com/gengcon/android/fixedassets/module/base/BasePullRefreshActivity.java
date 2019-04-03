package com.gengcon.android.fixedassets.module.base;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.base.BaseActivity;

public class BasePullRefreshActivity extends BaseActivity {

    protected final int NO_SEARCH = 0x01;
    protected final int NO_DATA = 0x02;
    protected final int NO_NET = 0x03;
    protected final int NORMAL = 0x04;

    protected LinearLayout mLlNoData, mLlNoSearch, mLlNoNet;
    protected Button reloadButton;

    @Override
    protected void initView() {
        mLlNoData = findViewById(R.id.ll_no_data);
        mLlNoSearch = findViewById(R.id.ll_no_search);
        mLlNoNet = findViewById(R.id.ll_no_net);
        reloadButton = findViewById(R.id.reloadButton);
    }

    @Override
    public void showErrorMsg(int status, String msg) {
        super.showErrorMsg(status, msg);
        if (!isNetworkConnected(this)){
            initDefault(NO_NET);
        }
    }

    @Override
    public void showCodeMsg(String code, String msg) {
        super.showCodeMsg(code, msg);
        if (!isNetworkConnected(this)){
            initDefault(NO_NET);
        }
    }

    protected void initDefault(int status) {
        mLlNoData.setVisibility(View.GONE);
        mLlNoSearch.setVisibility(View.GONE);
        mLlNoNet.setVisibility(View.GONE);
        if (status == NO_DATA) {
            mLlNoData.setVisibility(View.VISIBLE);
        } else if (status == NO_NET) {
            mLlNoNet.setVisibility(View.VISIBLE);
        } else if (status == NO_SEARCH) {
            mLlNoSearch.setVisibility(View.VISIBLE);
        }
    }
}
