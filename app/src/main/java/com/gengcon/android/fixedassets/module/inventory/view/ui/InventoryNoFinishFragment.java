package com.gengcon.android.fixedassets.module.inventory.view.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.common.module.htttp.URL;
import com.gengcon.android.fixedassets.module.approval.view.ui.AssetDetailsActivity;
import com.gengcon.android.fixedassets.module.base.BasePullRefreshFragment;
import com.gengcon.android.fixedassets.module.greendao.AssetBean;
import com.gengcon.android.fixedassets.module.inventory.view.OnItemClick;
import com.gengcon.android.fixedassets.module.inventory.widget.adapter.InventoryAssetAdapter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("ValidFragment")
public class InventoryNoFinishFragment extends BasePullRefreshFragment implements View.OnClickListener, OnItemClick {

    //    private SmartRefreshLayout mRefreshLayout;
    private MyRecyclerView mRecyclerView;
    private InventoryAssetAdapter mAdapter;
    private TextView inventory_wp, inventory_yp;
    private View v_wp, v_yp;
    private int status = 1;
    private String pd_no;


    private List<AssetBean> wp_assets;
    private List<AssetBean> yp_assets;

    @SuppressLint("ValidFragment")
    public InventoryNoFinishFragment(List<AssetBean> assetBeans, String pd_no) {
        wp_assets = new ArrayList<>();
        yp_assets = new ArrayList<>();
        this.pd_no = pd_no;
        for (int i = 0; i < assetBeans.size(); i++) {
            if (assetBeans.get(i).getPd_status() == 1) {
                wp_assets.add(assetBeans.get(i));
            } else {
                yp_assets.add(assetBeans.get(i));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asset_nofinish, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        inventory_wp = view.findViewById(R.id.inventory_wp);
        inventory_yp = view.findViewById(R.id.inventory_yp);
        v_wp = view.findViewById(R.id.v_wp);
        v_yp = view.findViewById(R.id.v_yp);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new InventoryAssetAdapter(getActivity());
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider2));
        mRecyclerView.addItemDecoration(divider);
        inventory_wp.setOnClickListener(this);
        inventory_yp.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initSelect() {
        initDefault(NORMAL);
        inventory_wp.setTextColor(getResources().getColor(R.color.black_text));
        v_wp.setBackgroundColor(getResources().getColor(R.color.white));
        inventory_yp.setTextColor(getResources().getColor(R.color.black_text));
        v_yp.setBackgroundColor(getResources().getColor(R.color.white));
        inventory_wp.setText(getString(R.string.inventory_wp) + "(" + wp_assets.size() + ")");
        inventory_yp.setText(getString(R.string.inventory_yp) + "(" + yp_assets.size() + ")");
        if (status == 1) {
            inventory_wp.setTextColor(getResources().getColor(R.color.blue));
            v_wp.setBackgroundColor(getResources().getColor(R.color.blue));
            if (wp_assets.size() == 0) {
                if (!isNetworkConnected(getActivity())) {
                    initDefault(NO_NET);
                } else {
                    initDefault(NO_DATA);
                }
            }
            mAdapter.addDataSource(wp_assets);
        } else if (status == 2) {
            inventory_yp.setTextColor(getResources().getColor(R.color.blue));
            v_yp.setBackgroundColor(getResources().getColor(R.color.blue));
            if (yp_assets.size() == 0) {
                if (!isNetworkConnected(getActivity())) {
                    initDefault(NO_NET);
                } else {
                    initDefault(NO_DATA);
                }
            }
            mAdapter.addDataSource(yp_assets);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSelect();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        if (isNetworkConnected(getActivity())) {
            initDefault(NORMAL);
        } else {
            initDefault(NO_NET);
        }
//        mRefreshLayout.finishRefresh();
    }

    @Override
    public void showErrorMsg(int status, String msg) {
        super.showErrorMsg(status, msg);
//        mRefreshLayout.finishRefresh();
//        mRefreshLayout.finishLoadmore();
    }

    @Override
    public void showCodeMsg(String code, String msg) {
        super.showCodeMsg(code, msg);
//        mRefreshLayout.finishRefresh();
//        mRefreshLayout.finishLoadmore();
    }

    @Override
    public void showInvalidType(int invalid_type) {
        super.showInvalidType(invalid_type);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inventory_wp:
                if (status != 1) {
                    status = 1;
                    initSelect();
                }
                break;
            case R.id.inventory_yp:
                if (status != 2) {
                    status = 2;
                    initSelect();
                }
                break;
        }
    }

    @Override
    public void onItemClick(String assetId) {
        Intent intent = new Intent(getActivity(), AssetDetailsActivity.class);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.INVENTORY_ASSET_DETAIL + assetId + "&doc_no=" + pd_no);
        startActivity(intent);
    }
}
