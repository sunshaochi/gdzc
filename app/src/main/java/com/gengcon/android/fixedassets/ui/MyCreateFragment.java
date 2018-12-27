package com.gengcon.android.fixedassets.ui;

import android.content.DialogInterface;
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
import com.gengcon.android.fixedassets.adapter.InventoryAdapter;
import com.gengcon.android.fixedassets.bean.Inventory;
import com.gengcon.android.fixedassets.bean.result.ResultInventorys;
import com.gengcon.android.fixedassets.presenter.InventoryFragmentListPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.RolePowerManager;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.InventoryListView;
import com.gengcon.android.fixedassets.view.ItemTouchListener;
import com.gengcon.android.fixedassets.widget.AlertDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class MyCreateFragment extends BasePullRefreshFragment implements ItemTouchListener, InventoryListView {

    private RefreshLayout mRefreshLayout;
    private SwipeMenuRecyclerView mRecyclerView;
    private InventoryAdapter mAdapter;
    private InventoryFragmentListPresenter mPresenter;
    private int mPage = 1;
    private boolean isViewPrepared = false;
    private InventoryListActivity activity;
    private boolean can_edit;
    private boolean can_del;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (InventoryListActivity) getActivity();
        mPresenter = new InventoryFragmentListPresenter();
        mPresenter.attachView(this);
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                mPresenter.getInventory(mPage, 2);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getInventory(++mPage, 2);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isViewPrepared = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRefreshLayout.autoRefresh();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (RolePowerManager.getInstance().isInventoryEdit()) {
            can_edit = true;
        } else {
            can_edit = false;
        }

        if (RolePowerManager.getInstance().isInventoryDel()) {
            can_del = true;
        } else {
            can_del = false;
        }
        mAdapter = new InventoryAdapter(getActivity(), 2, can_edit, can_del);
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
    public void onResume() {
        super.onResume();
        mRecyclerView.scrollToPosition(0);
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
        Intent intent = new Intent(getActivity(), EditInventoryActivity.class);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, inv_no);
        startActivity(intent);
    }

    @Override
    public void onDeleteMenuClick(String inv_no, int position) {
        showDeleteDialog(inv_no, position);
    }

    private void showDeleteDialog(final String docId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.tips);
        builder.setText(R.string.inventory_delete);
        builder.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, getString(R.string.cancel));
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.deleteInventory(docId, activity);
                mAdapter.removeAsset(position);
                if (mAdapter.isNoData()) {
                    initDefault(NO_DATA);
                }
            }
        }, getString(R.string.confirm));
        builder.show();
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
    public void hideLoading() {
        if (isNetworkConnected(getActivity())) {
            initDefault(NORMAL);
        } else {
            initDefault(NO_NET);
        }
        mRefreshLayout.finishRefresh();
    }
}
