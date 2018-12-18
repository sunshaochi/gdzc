package com.gengcon.android.fixedassets.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.adapter.AssetAdapter;
import com.gengcon.android.fixedassets.adapter.OnItemClickListener;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

public class AssetListActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener {

    private MyRecyclerView mRecyclerView;
    private AssetAdapter mAdapter;
    private EditText mEtSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_list);
        initView();
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.asset_list);
        ((TextView) findViewById(R.id.tv_title_right)).setText(R.string.select);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        mEtSearch = findViewById(R.id.et_search);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AssetAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);
        mAdapter.setPadding(true);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider1));
        mRecyclerView.addItemDecoration(divider);

        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.tv_title_right).setOnClickListener(this);
        findViewById(R.id.ll_search).setOnClickListener(this);
        findViewById(R.id.iv_filter).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.tv_title_right:
                break;
            case R.id.ll_search:
                break;
            case R.id.iv_filter:
                break;

        }
    }

    @Override
    public void onItemClick(int position) {
        //todo
        ToastUtils.toastMessage(this, position + "");
    }
}
