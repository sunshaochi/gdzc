package com.gengcon.android.fixedassets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.ApprovalNum;
import com.gengcon.android.fixedassets.presenter.ApprovalListPresenter;
import com.gengcon.android.fixedassets.util.CustomViewPager;
import com.gengcon.android.fixedassets.util.RolePowerManager;
import com.gengcon.android.fixedassets.view.ApprovalNumView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;


public class ApprovalListActivity extends BaseActivity implements View.OnClickListener, ApprovalNumView {

    private CustomViewPager approvalPager;
    private ApprovalPagerAdapter pagerAdapter;
    private ApprovalListPresenter approvalListPresenter;
    private TextView waitApprovalTitle, completeApprovalTitle;
    private FrameLayout waitApprovalLayout, completeApprovalLayout;
    private View waitApprovalView, completeApprovalView;
    String waitTitle = "待审批";
    String completeTitle = "已审批";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_list_test);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.approval_list);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_home);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        waitApprovalTitle = findViewById(R.id.waitApprovalTitle);
        completeApprovalTitle = findViewById(R.id.completeApprovalTitle);
        waitApprovalLayout = findViewById(R.id.waitApprovalLayout);
        completeApprovalLayout = findViewById(R.id.completeApprovalLayout);
        waitApprovalView = findViewById(R.id.waitApprovalView);
        completeApprovalView = findViewById(R.id.completeApprovalView);
        waitApprovalLayout.setOnClickListener(this);
        completeApprovalLayout.setOnClickListener(this);
        approvalPager = findViewById(R.id.approvalPager);
        approvalPager.setScanScroll(false);
        pagerAdapter = new ApprovalPagerAdapter(getSupportFragmentManager());
        approvalPager.setAdapter(pagerAdapter);
        waitApprovalTitle.setTextColor(getResources().getColor(R.color.blue));
        completeApprovalTitle.setTextColor(getResources().getColor(R.color.black));
        waitApprovalTitle.setText(waitTitle);
        completeApprovalTitle.setText(completeTitle);
        waitApprovalView.setVisibility(View.VISIBLE);
        approvalPager.setCurrentItem(0);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider1));
        findViewById(R.id.tv_title_right).setVisibility(RolePowerManager.getInstance().isInventoryAdd() ? View.VISIBLE : View.GONE);
    }

    public void initData() {
        approvalListPresenter = new ApprovalListPresenter();
        approvalListPresenter.attachView(this);
        approvalListPresenter.getApprovalNum();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                Intent intent1 = new Intent(ApprovalListActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.waitApprovalLayout:
                waitApprovalTitle.setTextColor(getResources().getColor(R.color.blue));
                completeApprovalTitle.setTextColor(getResources().getColor(R.color.black));
                waitApprovalView.setVisibility(View.VISIBLE);
                completeApprovalView.setVisibility(View.GONE);
                approvalPager.setCurrentItem(0);
                break;
            case R.id.completeApprovalLayout:
                completeApprovalTitle.setTextColor(getResources().getColor(R.color.blue));
                waitApprovalTitle.setTextColor(getResources().getColor(R.color.black));
                completeApprovalView.setVisibility(View.VISIBLE);
                waitApprovalView.setVisibility(View.GONE);
                approvalPager.setCurrentItem(1);
                break;
        }
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
    public void showApproval(final ApprovalNum approvalNum) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuilder taskTitleSB = new StringBuilder();
                StringBuilder createTitleSB = new StringBuilder();
                taskTitleSB.append(waitTitle);
                createTitleSB.append(completeTitle);
                final String newTaskTitle = taskTitleSB.append("(" + approvalNum.getPending_num() + ")").toString();
                final String newCreateTitle = createTitleSB.append("(" + approvalNum.getProcessed_num() + ")").toString();
                waitApprovalTitle.setText(newTaskTitle);
                completeApprovalTitle.setText(newCreateTitle);
            }
        });
    }

    public static class ApprovalPagerAdapter extends FragmentPagerAdapter {


        public ApprovalPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new WaitApprovalFragment();
            } else {
                return new CompleteApprovalFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}
