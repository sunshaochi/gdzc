package com.gengcon.android.fixedassets.module.inventory.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.module.base.GApplication;
import com.gengcon.android.fixedassets.module.greendao.AssetBean;
import com.gengcon.android.fixedassets.module.greendao.AssetBeanDao;
import com.gengcon.android.fixedassets.module.inventory.widget.adapter.SearchAssetAdapter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchAssetActivity extends BaseActivity implements View.OnClickListener, SearchAssetAdapter.SearchAssetCallback {

    private SearchAssetAdapter searchAssetAdapter;
    private RecyclerView recyclerView;
    private EditText searchEdit;
    private LinearLayout searchLayout;
    private String pd_no;
    private String user_id;
    private AssetBeanDao assetBeanDao;
    private List<AssetBean> assetBeans;
    private ArrayList<String> assets_ids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_asset);
        pd_no = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID);
        user_id = (String) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.USER_ID, "");
        assetBeanDao = GApplication.getDaoSession().getAssetBeanDao();
        assetBeans = new ArrayList<>();
        assets_ids = new ArrayList<>();
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.search_asset);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.sureButton).setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider4));
        recyclerView.addItemDecoration(divider);
        searchAssetAdapter = new SearchAssetAdapter(this);
        searchAssetAdapter.setCallBack(this);
        recyclerView.setAdapter(searchAssetAdapter);
        searchLayout = findViewById(R.id.searchLayout);
        searchLayout.setOnClickListener(this);
        searchEdit = findViewById(R.id.searchEdit);
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
                if (assets_ids.size() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("assets_ids", assets_ids);
                    setResult(Constant.RESULT_OK_INVENTORY_MANUAL, intent);
                    finish();
                } else {
                    onBackPressed();
                }
                break;
            case R.id.searchLayout:
                searchEdit.setFocusable(true);
                break;
            case R.id.sureButton:
                initDefault(NORMAL);
                assetBeans.clear();
                hideSoftInput();
                if (TextUtils.isEmpty(searchEdit.getText().toString())) {
                    assetBeans = assetBeanDao.queryBuilder().where(AssetBeanDao.Properties.User_id.eq(user_id))
                            .where(AssetBeanDao.Properties.Pd_no.eq(pd_no)).list();
                    if (assetBeans.size() == 0) {
                        initDefault(NO_DATA);
                    }
                } else {
                    assetBeans = assetBeanDao.queryBuilder().where(AssetBeanDao.Properties.User_id.eq(user_id))
                            .where(AssetBeanDao.Properties.Pd_no.eq(pd_no))
                            .whereOr(AssetBeanDao.Properties.Asset_code.like("%" + searchEdit.getText().toString() + "%")
                                    , AssetBeanDao.Properties.Asset_name.like("%" + searchEdit.getText().toString() + "%")).list();
                    if (assetBeans.size() == 0) {
                        initDefault(NO_DATA);
                    }
                }
                searchAssetAdapter.addDataSource(assetBeans);
                break;
        }
    }

    @Override
    public void clickItem() {

    }

    @Override
    public void clickInventoryButton(String asset_id) {
        AssetBean assetBean = assetBeanDao.queryBuilder()
                .where(assetBeanDao.queryBuilder().and(AssetBeanDao.Properties.Pd_no.eq(pd_no),
                        AssetBeanDao.Properties.User_id.eq(user_id),
                        AssetBeanDao.Properties.Asset_id.eq(asset_id)))
                .unique();
        assetBean.setPd_status(2);
        assetBean.setIsScanAsset(1);
        assetBeanDao.update(assetBean);
        assets_ids.add(asset_id);
    }
}
