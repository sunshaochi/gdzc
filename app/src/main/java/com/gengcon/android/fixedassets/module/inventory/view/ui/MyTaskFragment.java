package com.gengcon.android.fixedassets.module.inventory.view.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aitsuki.swipe.SwipeMenuRecyclerView;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.inventory.widget.adapter.InventoryAdapter;
import com.gengcon.android.fixedassets.bean.Inventory;
import com.gengcon.android.fixedassets.bean.result.ResultInventorys;
import com.gengcon.android.fixedassets.module.base.BasePullRefreshFragment;
import com.gengcon.android.fixedassets.module.inventory.view.InventoryListView;
import com.gengcon.android.fixedassets.module.inventory.presenter.InventoryFragmentListPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.common.ItemTouchListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class MyTaskFragment extends BasePullRefreshFragment implements View.OnClickListener, ItemTouchListener, InventoryListView {

    private SmartRefreshLayout mRefreshLayout;
    private SwipeMenuRecyclerView mRecyclerView;
    private InventoryAdapter mAdapter;
    private InventoryFragmentListPresenter mPresenter;
    private int mPage = 1;

    private boolean isViewPrepared = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new InventoryAdapter(getActivity(), 1, false, false);
        mAdapter.setItemTouchListener(this);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider1));
        mRecyclerView.addItemDecoration(divider);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setPrimaryColors(getResources().getColor(R.color.asset_gray));
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setTextSizeTitle(14).setEnableLastTime(false));
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isViewPrepared = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new InventoryFragmentListPresenter();
        mPresenter.attachView(this);
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                mPresenter.getInventory(mPage, 1);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getInventory(++mPage, 1);
            }
        });
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected(getActivity())) {
                    initDefault(NORMAL);
                    mRefreshLayout.autoRefresh();
                } else {
                    ToastUtils.toastMessage(getActivity(), "网络连接不给力,请检查您的网络");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isViewPrepared) {
            return;
        }
        if (isVisibleToUser) {
            if (isNetworkConnected(getActivity())) {
                initDefault(NORMAL);
                mRefreshLayout.autoRefresh();
            } else {
                initDefault(NO_NET);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    public void showInventorys(ResultInventorys resultInventorys) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
        if (mPage == 1) {
            mAdapter.changeDataSource(resultInventorys);
        } else {
            mAdapter.addDataSource(resultInventorys);
        }

        if (mPage >= resultInventorys.getPage_count()) {
            mRefreshLayout.setEnableLoadmore(false);
        } else {
            mRefreshLayout.setEnableLoadmore(true);
        }
        initDefault(mPage == 1 ? (resultInventorys.getList() != null && resultInventorys.getList().size() != 0) ? NORMAL : NO_DATA : NORMAL);
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
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void showErrorMsg(int status, String msg) {
        super.showErrorMsg(status, msg);
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
    }

    @Override
    public void showCodeMsg(String code, String msg) {
        super.showCodeMsg(code, msg);
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
    }

    @Override
    public void showInvalidType(int invalid_type) {
        super.showInvalidType(invalid_type);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(int position) {
        Inventory inventory = mAdapter.getItem(position);
//        if (!RolePowerManager.getInstance().check("inventory/getAssetList")) {
//            showPermissionDeniedTips();
//            return;
//        }
//        if (!RolePowerManager.getInstance().check("inventory/getResultDetail")) {
//            showPermissionDeniedTips();
//            return;
//        }
        Intent intent = null;
        if (inventory.getStatus() == Inventory.NOT_INVENTORY) {
            intent = new Intent(getActivity(), InventoryDetailsActivity.class);
        } else {
            intent = new Intent(getActivity(), InventoryResultActivity.class);
        }
        intent.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, inventory.getInv_no());
        startActivity(intent);
    }

    @Override
    public void onEditMenuClick(String inv_no) {

    }

    @Override
    public void onDeleteMenuClick(String inv_no, int position) {

    }
}