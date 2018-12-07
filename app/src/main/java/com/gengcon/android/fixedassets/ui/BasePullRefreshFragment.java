package com.gengcon.android.fixedassets.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseFragment;

public class BasePullRefreshFragment extends BaseFragment {

    protected final int NO_SEARCH = 0x01;
    protected final int NO_DATA = 0x02;
    protected final int NO_NET = 0x03;
    protected final int NORMAL = 0x04;

    protected LinearLayout mLlNoData, mLlNoSearch, mLlNoNet;
    protected Button reloadButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLlNoData = view.findViewById(R.id.ll_no_data);
        mLlNoSearch = view.findViewById(R.id.ll_no_search);
        mLlNoNet = view.findViewById(R.id.ll_no_net);
        reloadButton = view.findViewById(R.id.reloadButton);
    }

    @Override
    public void showErrorMsg(int code, String msg) {
        super.showErrorMsg(code, msg);
        initDefault(NO_NET);
    }

    @Override
    public void showCodeMsg(String code, String msg) {
        super.showCodeMsg(code, msg);
        initDefault(NO_NET);
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
