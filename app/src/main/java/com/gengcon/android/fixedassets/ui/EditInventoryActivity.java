package com.gengcon.android.fixedassets.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.adapter.EditInventoryAdapter;
import com.gengcon.android.fixedassets.adapter.OnItemClickListener;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.AssetBean;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.result.InventoryDetail;
import com.gengcon.android.fixedassets.bean.result.InventoryHeadDetail;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.presenter.EditInventoryPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.EditInventoryView;
import com.gengcon.android.fixedassets.widget.AlertDialog;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;
import com.gengcon.android.fixedassets.widget.PickerLinearLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

public class EditInventoryActivity extends BaseActivity implements View.OnClickListener, PickerLinearLayout.OnPickerListener, OnItemClickListener, EditInventoryView {

    private EditText mEtInputName, mEtRemarks;
    private TextView mTvInputUser, mTvEdit, mTvSelectSize;
    private PickerLinearLayout mLlPicker;
    private RefreshLayout mRefreshLayout;
    private MyRecyclerView mRecyclerView;
    private EditInventoryAdapter mAdapter;
    private ImageView mIvChoice;
    private int mMode = EditInventoryAdapter.NORMAL;
    private boolean mIsAllSelect = false;
    private ArrayList<AssetBean> mAssets;
    private boolean mIsCreated = false;
    private boolean editAssets = false;
    private int mPage = 1;
    private int user_id;

    private List<String> add_ids = new ArrayList<>();
    private List<String> del_ids = new ArrayList<>();

    private int totalPage;

    private int assetSize;

    private String inv_no;

    private EditInventoryPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inventory);
        mAssets = new ArrayList<>();
        initIntent(getIntent());
        initView();
    }

    private void initIntent(Intent intent) {
        inv_no = intent.getStringExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID);
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.edit_inventory_list);
        ((TextView) findViewById(R.id.tv_title_right)).setText(R.string.save);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        mEtInputName = findViewById(R.id.et_input_name);
        mEtRemarks = findViewById(R.id.et_remarks);
        mTvInputUser = findViewById(R.id.tv_select_user);
        mLlPicker = findViewById(R.id.ll_picker);
        mTvEdit = findViewById(R.id.tv_edit);
        mIvChoice = findViewById(R.id.iv_choice);
        mTvSelectSize = findViewById(R.id.tv_select_size);

        presenter = new EditInventoryPresenter();
        presenter.attachView(this);
        presenter.getInventoryDetail(inv_no, mPage);
        presenter.getInventoryHead(inv_no);

        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (mPage <= totalPage) {
                    if (editAssets) {
                        presenter.getEditDeleteList(inv_no, add_ids, del_ids, mPage);
                    } else {
                        presenter.getInventoryDetail(inv_no, mPage);
                    }
                }
                mRefreshLayout.finishLoadmore();
            }
        });

        SpannableString spannableString = new SpannableString("*" + getString(R.string.inventory_name));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.alert)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_text)), 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) findViewById(R.id.tv_name)).setText(spannableString);
        spannableString = new SpannableString("*" + getString(R.string.distribution_user));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.alert)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_text)), 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) findViewById(R.id.tv_user)).setText(spannableString);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new EditInventoryAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.asset_divider2));
        mRecyclerView.addItemDecoration(divider);

        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.tv_title_right).setOnClickListener(this);
        findViewById(R.id.tv_select_user).setOnClickListener(this);
        findViewById(R.id.tv_select_user_layout).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.ll_choice).setOnClickListener(this);
        findViewById(R.id.btn_del).setOnClickListener(this);
        mTvEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title_right:
                if (checkParam()) {
                    presenter.updateInventory(inv_no, mEtInputName.getText().toString(), user_id, mEtRemarks.getText().toString(), add_ids, del_ids);
                }
                break;
            case R.id.tv_select_user_layout:
            case R.id.tv_select_user:
                hideSoftInput();
                presenter.getUsers();
                break;
            case R.id.btn_add:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.BECALL);
                intent.putExtra(Constant.INTENT_EXTRA_KEY_ASSETS, mAssets);
                intent.putExtra("webName", "资产列表");
                intent.putExtra("webTitle", "选择");
                intent.putExtra("intentType", "edit");
                startActivityForResult(intent, Constant.GET_ASSET_CODE);
                break;
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.tv_edit:
                if (mMode == EditInventoryAdapter.NORMAL) {
                    findViewById(R.id.ll_add).setVisibility(View.GONE);
                    findViewById(R.id.ll_all_select).setVisibility(View.VISIBLE);
                    mMode = EditInventoryAdapter.CHOICE;
                    mTvEdit.setText(R.string.complete);
                } else {
                    findViewById(R.id.ll_add).setVisibility(View.VISIBLE);
                    findViewById(R.id.ll_all_select).setVisibility(View.GONE);
                    mMode = EditInventoryAdapter.NORMAL;
                    mTvEdit.setText(R.string.edit);
                    mIsAllSelect = false;
                    mIvChoice.setImageResource(R.drawable.ic_unchecked);
                }
                mAdapter.setMode(mMode);
                break;
            case R.id.ll_choice:
                mIsAllSelect = !mIsAllSelect;
                mIvChoice.setImageResource(mIsAllSelect ? R.drawable.ic_select : R.drawable.ic_unchecked);
                if (mIsAllSelect) {
                    mAdapter.allSelect();
                } else {
                    mAdapter.unSelect();
                }
                break;
            case R.id.btn_del:
                if (mAdapter.isChoiceAsset()) {
                    editAssets = true;
                    del_ids.addAll(mAdapter.getDelIds());
                    for (int i = 0; i < del_ids.size(); i++) {
                        if (add_ids.contains(del_ids.get(i))) {
                            add_ids.remove(del_ids.get(i));
                        }
                    }
                    mPage = 1;
                    presenter.getEditDeleteList(inv_no, add_ids, del_ids, mPage);
                    mIsAllSelect = false;
                    mIvChoice.setImageResource(R.drawable.ic_unchecked);
                } else {
                    ToastUtils.toastMessage(this, "请选择要删除的资产");
                    return;
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.GET_ASSET_CODE && resultCode == RESULT_OK) {
            List<String> addIds = (ArrayList<String>) data.getSerializableExtra(Constant.INTENT_EXTRA_KEY_ASSETS);
            add_ids.addAll(addIds);
            for (int i = 0; i < add_ids.size(); i++) {
                if (del_ids.contains(add_ids.get(i))) {
                    del_ids.remove(add_ids.get(i));
                }
            }
            mPage = 1;
            presenter.getEditDeleteList(inv_no, add_ids, del_ids, mPage);
            editAssets = true;
        }
    }

    private void showSavedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tips);
        builder.setText(R.string.inventoty_list_save_successed);
//        builder.setNegativeButton(new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        }, getString(R.string.cancel));
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(EditInventoryActivity.this, InventoryListActivity.class);
                startActivity(intent);
            }
        }, getString(R.string.back_list));
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (mLlPicker.getVisibility() == View.VISIBLE) {
            mLlPicker.setVisibility(View.GONE);
            return;
        }
        if (mIsCreated) {
            Intent intent = new Intent(EditInventoryActivity.this, InventoryListActivity.class);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfirm(User user) {
        user_id = user.getId();
        mTvInputUser.setText(user.getUser_name());
        mTvInputUser.setTextColor(getResources().getColor(R.color.black_text));
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onItemClick(int position) {
        if (mAdapter.getMode() == EditInventoryAdapter.NORMAL) {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.BEDETAIL);
            intent.putExtra(Constant.INTENT_EXTRA_KEY_ASSER_ID, mAssets.get(position).getAsset_id());
            intent.putExtra(Constant.INTENT_IS_HISTORY_ASSER_ID, "0");
            intent.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, inv_no);
            intent.putExtra("webName", "资产详情");
            startActivity(intent);
        } else {
            mAdapter.changeSelect(position);
            mIsAllSelect = mAdapter.isAllSelect();
            mIvChoice.setImageResource(mIsAllSelect ? R.drawable.ic_select : R.drawable.ic_unchecked);
        }
    }

    @Override
    public void updateInventoryResult(Bean bean) {
        mIsCreated = true;
        showSavedDialog();
    }

    @Override
    public void showInventoryResult(InventoryDetail inventoryDetail) {
        mAssets.clear();
        List<AssetBean> assets = inventoryDetail.getList();
        assetSize = inventoryDetail.getAll_ids().size();
        mAdapter.changeDataSource(assets);
        mAssets.addAll(assets);
        SpannableString spannableString = new SpannableString(getString(R.string.already_chosen) + getString(R.string.pending_inventory_assets) + assetSize + getString(R.string.item));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray_text)), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 8, spannableString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray_text)), spannableString.length() - 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvSelectSize.setText(spannableString);
        totalPage = inventoryDetail.getTotal();
        if (mPage <= totalPage) {
            mPage = ++mPage;
        }
        if (mAssets.size() > 0) {
            findViewById(R.id.ll_asset).setVisibility(View.VISIBLE);
            findViewById(R.id.v_divider).setVisibility(View.GONE);
        } else {
            mAdapter.clear();
            findViewById(R.id.ll_asset).setVisibility(View.GONE);
            findViewById(R.id.v_divider).setVisibility(View.VISIBLE);
        }
        mIsCreated = true;
    }

    @Override
    public void showInventoryMoreResult(InventoryDetail inventoryDetail) {
        List<AssetBean> assets = inventoryDetail.getList();
        mAdapter.addDataSource(assets);
        mAssets.addAll(assets);
        totalPage = inventoryDetail.getTotal();
        if (mPage <= totalPage) {
            mPage = ++mPage;
        }
    }

    @Override
    public void initUsers(List<User> users) {
        mLlPicker.setVisibility(View.VISIBLE);
        mLlPicker.setData(users);
        mLlPicker.setOnSelectListener(this);
    }

    @Override
    public void initHeadDetail(InventoryHeadDetail headDetail) {
        mEtInputName.setText(headDetail.getInv_name());
        mTvInputUser.setText(headDetail.getAllot_username());
        mTvInputUser.setTextColor(getResources().getColor(R.color.black_text));
        mEtRemarks.setText(headDetail.getRemark());
        user_id = headDetail.getAllot_userid();
    }

    private boolean checkParam() {
        if (TextUtils.isEmpty(mEtInputName.getText().toString())) {
            ToastUtils.toastMessage(this, R.string.inventory_name_notnull);
            return false;
        }
        if (mEtInputName.getText().toString().length() > 40) {
            ToastUtils.toastMessage(this, R.string.inventory_name_length);
            return false;
        }
        if (mAssets.size() == 0) {
            ToastUtils.toastMessage(this, R.string.asset_not_null);
            return false;
        }
        return true;
    }

}
