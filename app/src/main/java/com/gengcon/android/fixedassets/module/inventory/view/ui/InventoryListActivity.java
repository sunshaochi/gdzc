package com.gengcon.android.fixedassets.module.inventory.view.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.ResultInventory;
import com.gengcon.android.fixedassets.common.ItemTouchListener;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.module.base.GApplication;
import com.gengcon.android.fixedassets.module.greendao.InventoryBean;
import com.gengcon.android.fixedassets.module.greendao.InventoryBeanDao;
import com.gengcon.android.fixedassets.module.inventory.view.InventoryListView;
import com.gengcon.android.fixedassets.module.inventory.presenter.InventoryListPresenter;
import com.gengcon.android.fixedassets.module.inventory.widget.adapter.InventoryAdapter;
import com.gengcon.android.fixedassets.module.main.view.ui.MainActivity;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.RolePowerManager;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class InventoryListActivity extends BaseActivity implements View.OnClickListener, InventoryListView, ItemTouchListener {

    private MyRecyclerView recyclerView;
    private InventoryAdapter mAdapter;
    private InventoryListPresenter inventoryListPresenter;
    private TextView noFinishText, finishedText;
    private FrameLayout noFinishLayout, finishedLayout;
    private View noFinishView, finishedView;
    String noFinishTitle = "未完成";
    String finishedTitle = "已完成";
    private InventoryBeanDao inventoryBeanDao;
    private List<InventoryBean> noFinishList;
    private List<InventoryBean> finishedList;
    private List<InventoryBean> dbInventories;
    private List<InventoryBean> inventoryBeanList;
    private String user_id;
    private int isFinish = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);
        user_id = (String) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.USER_ID, "");
        inventoryBeanDao = GApplication.getDaoSession().getInventoryBeanDao();
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.inventory_list);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_home);
        findViewById(R.id.tv_title_right).setOnClickListener(this);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        noFinishText = findViewById(R.id.noFinishText);
        finishedText = findViewById(R.id.finishedText);
        noFinishLayout = findViewById(R.id.noFinishLayout);
        finishedLayout = findViewById(R.id.finishedLayout);
        noFinishView = findViewById(R.id.noFinishView);
        finishedView = findViewById(R.id.finishedView);
        noFinishLayout.setOnClickListener(this);
        finishedLayout.setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new InventoryAdapter(this);
        mAdapter.setItemTouchListener(this);
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider3));
        recyclerView.addItemDecoration(divider);
        noFinishText.setTextColor(getResources().getColor(R.color.blue));
        finishedText.setTextColor(getResources().getColor(R.color.black));
        noFinishView.setVisibility(View.VISIBLE);
        findViewById(R.id.tv_title_right).setVisibility(RolePowerManager.getInstance().isInventoryAdd() ? View.VISIBLE : View.GONE);
        initData();
    }

    public void initData() {
        dbInventories = inventoryBeanDao.queryBuilder()
                .where(InventoryBeanDao.Properties.User_id.eq(user_id))
                .orderDesc(InventoryBeanDao.Properties.Created_at)
                .list();
        noFinishList = new ArrayList<>();
        finishedList = new ArrayList<>();
        if (!isNetworkConnected(this)) {
            if (dbInventories.size() == 0) {
                ToastUtils.toastMessage(this, "请检查网络");
            } else {
                for (int i = 0; i < dbInventories.size(); i++) {
                    if (dbInventories.get(i).getStatus() == 1 || dbInventories.get(i).getStatus() == 2) {
                        noFinishList.add(dbInventories.get(i));
                    } else {
                        finishedList.add(dbInventories.get(i));
                    }
                }
                noFinishText.setText(noFinishTitle + "(" + noFinishList.size() + ")");
                finishedText.setText(finishedTitle + "(" + finishedList.size() + ")");
            }
            initSelect();
        } else {
            inventoryListPresenter = new InventoryListPresenter();
            inventoryListPresenter.attachView(this);
            inventoryListPresenter.getOffnetList();
        }
    }

    private void initSelect() {
        noFinishText.setTextColor(getResources().getColor(R.color.black_text));
        noFinishView.setBackgroundColor(getResources().getColor(R.color.white));
        finishedText.setTextColor(getResources().getColor(R.color.black_text));
        finishedView.setBackgroundColor(getResources().getColor(R.color.white));
        noFinishText.setText(noFinishTitle + "(" + noFinishList.size() + ")");
        finishedText.setText(finishedTitle + "(" + finishedList.size() + ")");
        if (isFinish == 1) {
            finishedText.setTextColor(getResources().getColor(R.color.blue));
            finishedView.setBackgroundColor(getResources().getColor(R.color.blue));
            mAdapter.addDataSource(finishedList);
        } else if (isFinish == 2) {
            noFinishText.setTextColor(getResources().getColor(R.color.blue));
            noFinishView.setBackgroundColor(getResources().getColor(R.color.blue));
            mAdapter.addDataSource(noFinishList);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                Intent intent1 = new Intent(InventoryListActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.noFinishLayout:
                if (isFinish != 2) {
                    isFinish = 2;
                    initSelect();
                }
                recyclerView.scrollToPosition(0);
                break;
            case R.id.finishedLayout:
                if (isFinish != 1) {
                    isFinish = 1;
                    initSelect();
                }
                recyclerView.scrollToPosition(0);
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
    public void showOffnetList(ResultInventory resultInventory) {
        inventoryBeanList = new ArrayList<>();
        inventoryBeanList.addAll(resultInventory.getList());
        List<InventoryBean> addList = new ArrayList<>();
        List<InventoryBean> deleteList = new ArrayList<>();
        for (int i = 0; i < inventoryBeanList.size(); i++) {
            inventoryBeanList.get(i).setUser_id(user_id);
            inventoryBeanList.get(i).setTag(user_id + inventoryBeanList.get(i).getPd_no());
        }
        if (dbInventories.size() == 0) {
            inventoryBeanDao.insertOrReplaceInTx(inventoryBeanList);
            for (int i = 0; i < inventoryBeanList.size(); i++) {
                if (inventoryBeanList.get(i).getStatus() == 1 || inventoryBeanList.get(i).getStatus() == 2) {
                    noFinishList.add(inventoryBeanList.get(i));
                } else {
                    finishedList.add(inventoryBeanList.get(i));
                }
            }
        } else {
            List<String> pd_no_db = new ArrayList<>();
            List<String> pd_no_all = new ArrayList<>();
            List<InventoryBean> inventoriesFinishedOrWait = new ArrayList<>();
            for (int i = 0; i < dbInventories.size(); i++) {
                pd_no_db.add(dbInventories.get(i).getPd_no());
            }
            for (int i = 0; i < inventoryBeanList.size(); i++) {
                pd_no_all.add(inventoryBeanList.get(i).getPd_no());
            }
            for (int i = 0; i < inventoryBeanList.size(); i++) {
                if (!pd_no_db.contains(inventoryBeanList.get(i).getPd_no())) {
                    addList.add(inventoryBeanList.get(i));
                }
            }
            inventoryBeanDao.insertOrReplaceInTx(addList);
            for (int i = 0; i < dbInventories.size(); i++) {
                if (!pd_no_all.contains(dbInventories.get(i).getPd_no())) {
                    deleteList.add(dbInventories.get(i));
                }
            }
            inventoryBeanDao.deleteInTx(deleteList);
            for (int i = 0; i < inventoryBeanList.size(); i++) {
                if (inventoryBeanList.get(i).getSon_status() != 1) {
                    inventoriesFinishedOrWait.add(inventoryBeanList.get(i));
                }
            }
            inventoryBeanDao.insertOrReplaceInTx(inventoriesFinishedOrWait);
            List<InventoryBean> userInventoryList = inventoryBeanDao.queryBuilder().where(InventoryBeanDao.Properties.User_id.eq(user_id))
                    .orderDesc(InventoryBeanDao.Properties.Created_at).list();
            for (int i = 0; i < userInventoryList.size(); i++) {
                if (userInventoryList.get(i).getStatus() == 1 || userInventoryList.get(i).getStatus() == 2) {
                    noFinishList.add(userInventoryList.get(i));
                } else {
                    finishedList.add(userInventoryList.get(i));
                }
            }
        }
        noFinishText.setText(noFinishTitle + "(" + noFinishList.size() + ")");
        finishedText.setText(finishedTitle + "(" + finishedList.size() + ")");
        initSelect();
    }

    @Override
    public void onItemClick(int position) {
        InventoryBean inventory = mAdapter.getItem(position);
        Intent intent = new Intent(this, InventoryResultActivity.class);
        intent.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, inventory.getPd_no());
        intent.putExtra("pd_name", inventory.getPd_name());
        intent.putExtra("pd_status", inventory.getSon_status());
        startActivityForResult(intent, Constant.REQUEST_CODE_INVENTORY_PD_NUM);
    }

}
