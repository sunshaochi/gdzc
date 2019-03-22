package com.gengcon.android.fixedassets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.adapter.ApprovalListAdapter;
import com.gengcon.android.fixedassets.bean.result.Approval;
import com.gengcon.android.fixedassets.bean.result.ResultApprovals;
import com.gengcon.android.fixedassets.presenter.ApprovalFragmentListPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.ApprovalListView;
import com.gengcon.android.fixedassets.view.ItemTouchListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CompleteApprovalFragment extends BasePullRefreshFragment implements ItemTouchListener, ApprovalListView {

    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ApprovalListAdapter mAdapter;
    private ApprovalFragmentListPresenter mPresenter;
    private ApprovalListActivity activity;
    private int mPage = 1;
    private boolean isViewPrepared = false;
    private boolean isFristLoad = true;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (ApprovalListActivity) getActivity();
        mPresenter = new ApprovalFragmentListPresenter();
        mPresenter.attachView(this);
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getApprovalList(++mPage, 2);
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
        View view = inflater.inflate(R.layout.fragment_approval_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ApprovalListAdapter(getActivity(), 2);
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
                activity.initData();
                if (isFristLoad) {
                    mPage = 1;
                    mPresenter.getApprovalList(mPage, 2);
                    isFristLoad = false;
                }
                mRefreshLayout.autoRefresh();
                mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                    @Override
                    public void onRefresh(RefreshLayout refreshlayout) {
                        mPage = 1;
                        mPresenter.getApprovalList(mPage, 2);
                    }
                });
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
        Approval approval = mAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), ApprovalDetailActivity.class);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_APPROVAL_TYPE, 2);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_APPROVAL_ID, approval.getDoc_no());
        startActivity(intent);
    }

    @Override
    public void onEditMenuClick(String inv_no) {

    }

    @Override
    public void onDeleteMenuClick(String inv_no, int position) {

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
    public void hideLoading() {
        if (isNetworkConnected(getActivity())) {
            initDefault(NORMAL);
        } else {
            initDefault(NO_NET);
        }
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void showApprovalList(ResultApprovals resultApprovals) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
        if (mPage == 1) {
            mAdapter.changeDataSource(resultApprovals);
        } else {
            mAdapter.addDataSource(resultApprovals);
        }

        if (mPage >= resultApprovals.getPage_count()) {
            mRefreshLayout.setEnableLoadmore(false);
        } else {
            mRefreshLayout.setEnableLoadmore(true);
        }
        initDefault(mPage == 1 ? (resultApprovals.getList() != null && resultApprovals.getList().size() != 0) ? NORMAL : NO_DATA : NORMAL);
    }
}
