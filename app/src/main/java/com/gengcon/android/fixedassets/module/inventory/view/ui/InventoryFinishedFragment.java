package com.gengcon.android.fixedassets.module.inventory.view.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.common.OnItemClickListener;
import com.gengcon.android.fixedassets.module.base.BasePullRefreshFragment;
import com.gengcon.android.fixedassets.module.greendao.AssetBean;
import com.gengcon.android.fixedassets.module.inventory.widget.adapter.InventoryAssetAdapter;
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
public class InventoryFinishedFragment extends BasePullRefreshFragment implements View.OnClickListener, OnItemClickListener {

    //    private RefreshLayout mRefreshLayout;
    private MyRecyclerView mRecyclerView;
    private InventoryAssetAdapter mAdapter;

    private TextView inventory_zc, inventory_py, inventory_pk;
    private View v_zc, v_py, v_pk;
    private int status = 2;

    private List<AssetBean> zc_assets;
    private List<AssetBean> py_assets;
    private List<AssetBean> pk_assets;

    @SuppressLint("ValidFragment")
    public InventoryFinishedFragment(List<AssetBean> assetBeans) {
        zc_assets = new ArrayList<>();
        py_assets = new ArrayList<>();
        pk_assets = new ArrayList<>();
        for (int i = 0; i < assetBeans.size(); i++) {
            if (assetBeans.get(i).getPd_status() == 1) {
                pk_assets.add(assetBeans.get(i));
            } else if (assetBeans.get(i).getPd_status() == 2) {
                zc_assets.add(assetBeans.get(i));
            } else if (assetBeans.get(i).getPd_status() == 3) {
                py_assets.add(assetBeans.get(i));
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSelect();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
//        mRefreshLayout.autoRefresh();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asset_finished, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        inventory_zc = view.findViewById(R.id.inventory_zc);
        inventory_py = view.findViewById(R.id.inventory_py);
        inventory_pk = view.findViewById(R.id.inventory_pk);
        v_zc = view.findViewById(R.id.v_zc);
        v_py = view.findViewById(R.id.v_py);
        v_pk = view.findViewById(R.id.v_pk);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new InventoryAssetAdapter(getActivity());
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider2));
        mRecyclerView.addItemDecoration(divider);
        inventory_zc.setOnClickListener(this);
        inventory_py.setOnClickListener(this);
        inventory_pk.setOnClickListener(this);
        return view;
    }

    private void initSelect() {
        initDefault(NORMAL);
        inventory_zc.setTextColor(getResources().getColor(R.color.black_text));
        v_zc.setBackgroundColor(getResources().getColor(R.color.white));
        inventory_py.setTextColor(getResources().getColor(R.color.black_text));
        v_py.setBackgroundColor(getResources().getColor(R.color.white));
        inventory_pk.setTextColor(getResources().getColor(R.color.black_text));
        v_pk.setBackgroundColor(getResources().getColor(R.color.white));
        inventory_zc.setText(getString(R.string.inventory_zc) + "(" + zc_assets.size() + ")");
        inventory_py.setText(getString(R.string.inventory_py) + "(" + py_assets.size() + ")");
        inventory_pk.setText(getString(R.string.inventory_pk) + "(" + pk_assets.size() + ")");
        if (status == 1) {
            inventory_pk.setTextColor(getResources().getColor(R.color.blue));
            v_pk.setBackgroundColor(getResources().getColor(R.color.blue));
            if (pk_assets.size() == 0){
                if (!isNetworkConnected(getActivity())) {
                    initDefault(NO_NET);
                } else {
                    initDefault(NO_DATA);
                }
            }
            mAdapter.addDataSource(pk_assets);
        } else if (status == 2) {
            inventory_zc.setTextColor(getResources().getColor(R.color.blue));
            v_zc.setBackgroundColor(getResources().getColor(R.color.blue));
            if (zc_assets.size() == 0){
                if (!isNetworkConnected(getActivity())) {
                    initDefault(NO_NET);
                } else {
                    initDefault(NO_DATA);
                }
            }
            mAdapter.addDataSource(zc_assets);
        } else if (status == 3) {
            inventory_py.setTextColor(getResources().getColor(R.color.blue));
            v_py.setBackgroundColor(getResources().getColor(R.color.blue));
            if (py_assets.size() == 0){
                if (!isNetworkConnected(getActivity())) {
                    initDefault(NO_NET);
                } else {
                    initDefault(NO_DATA);
                }
            }
            mAdapter.addDataSource(py_assets);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.scrollToPosition(0);
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
    public void onItemClick(int position) {

    }

    @Override
    public void showErrorMsg(int status, String msg) {
        super.showErrorMsg(status, msg);
    }

    @Override
    public void showCodeMsg(String code, String msg) {
        super.showCodeMsg(code, msg);
    }

    @Override
    public void showInvalidType(int invalid_type) {
        super.showInvalidType(invalid_type);
    }

    @Override
    public void hideLoading() {
        if (isNetworkConnected(getActivity())) {
            initDefault(NORMAL);
        } else {
            initDefault(NO_NET);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inventory_zc:
                if (status != 2) {
                    status = 2;
                    initSelect();
                }
                break;
            case R.id.inventory_py:
                if (status != 3) {
                    status = 3;
                    initSelect();
                }
                break;
            case R.id.inventory_pk:
                if (status != 1) {
                    status = 1;
                    initSelect();
                }
                break;
        }
    }
}
