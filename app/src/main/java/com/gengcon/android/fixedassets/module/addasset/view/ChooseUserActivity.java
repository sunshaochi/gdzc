package com.gengcon.android.fixedassets.module.addasset.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.ChooseUserBean;
import com.gengcon.android.fixedassets.module.addasset.presenter.ChooseUserPresenter;
import com.gengcon.android.fixedassets.module.addasset.widget.adapter.ChooseUserAdapter;
import com.gengcon.android.fixedassets.module.base.BaseActivity;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ChooseUserActivity extends BaseActivity implements View.OnClickListener, ChooseUserView, ChooseUserAdapter.ChooseUserCallback {

    private ChooseUserAdapter chooseUserAdapter;
    private ChooseUserPresenter chooseUserPresenter;
    private RecyclerView recyclerView;
    private EditText searchEdit;
    private LinearLayout searchLayout;
    private String org_id;
    private Button clearButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
        org_id = getIntent().getStringExtra("org_id");
        chooseUserPresenter = new ChooseUserPresenter();
        chooseUserPresenter.attachView(this);
        chooseUserPresenter.getAssetUser("", org_id);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.choose_user);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider2));
        recyclerView.addItemDecoration(divider);
        chooseUserAdapter = new ChooseUserAdapter(this);
        chooseUserAdapter.setCallBack(this);
        recyclerView.setAdapter(chooseUserAdapter);
        searchLayout = findViewById(R.id.searchLayout);
        searchLayout.setOnClickListener(this);
        searchEdit = findViewById(R.id.searchEdit);

        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(this);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String wd = s.toString();
                if (wd.length() > 0) {
                    clearButton.setVisibility(View.VISIBLE);
                } else {
                    clearButton.setVisibility(View.GONE);
                }
                chooseUserPresenter.getAssetUser(wd, org_id);
            }
        });
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
            case R.id.clearButton:
                searchEdit.setText("");
                break;
        }
    }

    @Override
    public void showUser(List<ChooseUserBean> chooseUserBeans) {
        chooseUserAdapter.addDataSource(chooseUserBeans);
    }

    @Override
    public void clickItem(int userId, String userName) {
        Intent intent = new Intent();
        intent.putExtra("id", userId);
        intent.putExtra("name", userName);
        setResult(AddAssetActivity.RESULT_OK_CHOOSE_USER, intent);
        finish();
    }
}
