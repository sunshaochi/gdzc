package com.gengcon.android.fixedassets.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.ResultInventorysNum;
import com.gengcon.android.fixedassets.presenter.InventoryListPresenter;
import com.gengcon.android.fixedassets.util.CustomViewPager;
import com.gengcon.android.fixedassets.util.RolePowerManager;
import com.gengcon.android.fixedassets.view.InventoryNumView;


public class InventoryListActivity extends BaseActivity implements View.OnClickListener, InventoryNumView {

    private CustomViewPager assetPager;
    private AssetPagerAdapter pagerAdapter;
    private InventoryListPresenter inventoryListPresenter;
    private TextView myTaskTitle, myCreateTitle;
    private FrameLayout myTaskLayout, myCreateLayout;
    private View myTaskView, myCreateView;
    String taskTitle = "我的任务";
    String createTitle = "我创建的";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.inventory_list);
        ((TextView) findViewById(R.id.tv_title_right)).setText(R.string.new_create);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_home);
        findViewById(R.id.tv_title_right).setOnClickListener(this);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        myTaskTitle = findViewById(R.id.myTaskTitle);
        myCreateTitle = findViewById(R.id.createTitle);
        myTaskLayout = findViewById(R.id.myTaskLayout);
        myCreateLayout = findViewById(R.id.myCreateLayout);
        myTaskView = findViewById(R.id.myTaskView);
        myCreateView = findViewById(R.id.myCreateView);
        myTaskLayout.setOnClickListener(this);
        myCreateLayout.setOnClickListener(this);
        assetPager = findViewById(R.id.assetPager);
        assetPager.setScanScroll(false);
        pagerAdapter = new AssetPagerAdapter(getSupportFragmentManager());
        assetPager.setAdapter(pagerAdapter);
        myTaskTitle.setTextColor(getResources().getColor(R.color.blue));
        myCreateTitle.setTextColor(getResources().getColor(R.color.black));
        myTaskTitle.setText(taskTitle);
        myCreateTitle.setText(createTitle);
        myTaskView.setVisibility(View.VISIBLE);
        assetPager.setCurrentItem(0);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider1));
        findViewById(R.id.tv_title_right).setVisibility(RolePowerManager.getInstance().isInventoryAdd() ? View.VISIBLE : View.GONE);
        initData();
    }

    public void initData() {
        inventoryListPresenter = new InventoryListPresenter();
        inventoryListPresenter.attachView(this);
        inventoryListPresenter.getInventorysNum();
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
            case R.id.tv_title_right:
                Intent intent = new Intent(InventoryListActivity.this, CreateInventoryActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.myTaskLayout:
                myTaskTitle.setTextColor(getResources().getColor(R.color.blue));
                myCreateTitle.setTextColor(getResources().getColor(R.color.black));
                myTaskView.setVisibility(View.VISIBLE);
                myCreateView.setVisibility(View.GONE);
                assetPager.setCurrentItem(0);
                break;
            case R.id.myCreateLayout:
                myCreateTitle.setTextColor(getResources().getColor(R.color.blue));
                myTaskTitle.setTextColor(getResources().getColor(R.color.black));
                myCreateView.setVisibility(View.VISIBLE);
                myTaskView.setVisibility(View.GONE);
                assetPager.setCurrentItem(1);
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
    public void showInventorys(final ResultInventorysNum resultInventorysNum) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuilder taskTitleSB = new StringBuilder();
                StringBuilder createTitleSB = new StringBuilder();
                taskTitleSB.append(taskTitle);
                createTitleSB.append(createTitle);
                final String newTaskTitle = taskTitleSB.append("(" + resultInventorysNum.getTask_num() + ")").toString();
                final String newCreateTitle = createTitleSB.append("(" + resultInventorysNum.getCreate_num() + ")").toString();
                myTaskTitle.setText(newTaskTitle);
                myCreateTitle.setText(newCreateTitle);
            }
        });
    }

    public static class AssetPagerAdapter extends FragmentPagerAdapter {


        public AssetPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new MyTaskFragment();
            } else {
                return new MyCreateFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}
