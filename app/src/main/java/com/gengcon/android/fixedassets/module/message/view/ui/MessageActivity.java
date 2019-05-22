package com.gengcon.android.fixedassets.module.message.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.message.widget.adapter.MessageAdapter;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.MessageBean;
import com.gengcon.android.fixedassets.common.module.http.URL;
import com.gengcon.android.fixedassets.module.message.presenter.MessagePresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.module.message.view.MessageView;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MessageActivity extends BaseActivity implements View.OnClickListener, MessageView, MessageAdapter.MessageCallback {

    private MessageAdapter messageAdapter;
    private MessagePresenter messagePresenter;
    private RefreshLayout mRefreshLayout;
    private List<MessageBean.ListBean> messageList;
    private MyRecyclerView recyclerView;
    private LinearLayout noDataLayout;
    //    private HomePresenter homePresenter;
    private int pageCount;
    private int mPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageList = new ArrayList<>();
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.message);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_home);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        noDataLayout = findViewById(R.id.ll_no_data);
        recyclerView = findViewById(R.id.recyclerview);

        messagePresenter = new MessagePresenter();
//        homePresenter = new HomePresenter();
        messagePresenter.attachView(this);
        messagePresenter.getUserNotice(mPage);

        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (mPage <= pageCount) {
                    messagePresenter.getUserNotice(mPage);
                } else {
                    mRefreshLayout.setEnableLoadmore(false);
                }
                mRefreshLayout.finishLoadmore();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        messageAdapter = new MessageAdapter(this);
        messageAdapter.setCallBack(this);
        recyclerView.setAdapter(messageAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
        }
    }

    @Override
    public void showMessage(MessageBean message) {
        if (message.getList() != null && message.getList().size() > 0) {
            noDataLayout.setVisibility(View.GONE);
            messageList = message.getList();
            messageAdapter.addDataSource(messageList);
            pageCount = message.getPage_count();
            if (mPage <= pageCount) {
                mPage = ++mPage;
            }
        } else {
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMoreMessage(MessageBean message) {
        if (message.getList() != null && message.getList().size() > 0) {
            noDataLayout.setVisibility(View.GONE);
            messageAdapter.addMoreDataSource(message.getList());
            pageCount = message.getPage_count();
            if (mPage <= pageCount) {
                mPage = ++mPage;
            }
        }
    }

    @Override
    public void clickMessage(int msgId) {
//        homePresenter.getReadEditNotice(msgId);
        Intent webIntent = new Intent(MessageActivity.this, MessageDetailsActivity.class);
        webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.MESSAGEDETAIL + msgId);
        startActivity(webIntent);
    }
}
