package com.gengcon.android.fixedassets.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.adapter.AssetAdapter;
import com.gengcon.android.fixedassets.adapter.OnItemClickListener;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.AssetBean;
import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.presenter.CreateInventoryPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.view.CreateInventoryView;
import com.gengcon.android.fixedassets.widget.AlertDialog;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;
import com.gengcon.android.fixedassets.widget.PickerLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CreateInventoryActivity extends BaseActivity implements View.OnClickListener, PickerLinearLayout.OnPickerListener, OnItemClickListener, CreateInventoryView {

    private EditText mEtInputName, mEtRemarks;
    private TextView mTvInputUser, mTvEdit, mTvSelectSize;
    private PickerLinearLayout mLlPicker;
    private MyRecyclerView mRecyclerView;
    private AssetAdapter mAdapter;
    private ImageView mIvChoice;
    private User mUser;
    private int mMode = AssetAdapter.NORMAL;
    private boolean mIsAllSelect = false;
    private ArrayList<AssetBean> mAssets;
    private boolean mIsCreated = false;
    private int assetSize;
    private int selectSize;
    private Button del_btn;

    private CreateInventoryPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_inventory);
        mAssets = new ArrayList<>();
        initView();

        presenter = new CreateInventoryPresenter();
        presenter.attachView(this);
        presenter.getUsers();
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.new_inventory_list);
        ((TextView) findViewById(R.id.tv_title_right)).setText(R.string.save);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        del_btn = findViewById(R.id.btn_del);
        mEtInputName = findViewById(R.id.et_input_name);
        mEtRemarks = findViewById(R.id.et_remarks);
        mTvInputUser = findViewById(R.id.tv_select_user);
        mLlPicker = findViewById(R.id.ll_picker);
        mTvEdit = findViewById(R.id.tv_edit);
        mIvChoice = findViewById(R.id.iv_choice);
        mTvSelectSize = findViewById(R.id.tv_select_size);

        SpannableString spannableString = new SpannableString("*" + getString(R.string.inventory_name));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.alert)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_text)), 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) findViewById(R.id.tv_name)).setText(spannableString);
        spannableString = new SpannableString("*" + getString(R.string.distribution_user));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.alert)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_text)), 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) findViewById(R.id.tv_user)).setText(spannableString);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AssetAdapter(this);
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
                if (checkParam())
                    presenter.addInventory(mEtInputName.getText().toString(), mUser.getId(), mEtRemarks.getText().toString(), getAssetIds());
                break;
            case R.id.tv_select_user_layout:
            case R.id.tv_select_user:
                hideSoftInput();
                break;
            case R.id.btn_add:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.BECALL);
                intent.putExtra(Constant.INTENT_EXTRA_KEY_ASSETS, getAssetIds());
                intent.putExtra("webName", "资产列表");
                intent.putExtra("webTitle", "选择");
                intent.putExtra("intentType", "create");
                startActivityForResult(intent, Constant.GET_ASSET_CODE);
                break;
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.tv_edit:
                del_btn.setText("删除");
                if (mMode == AssetAdapter.NORMAL) {
                    findViewById(R.id.ll_add).setVisibility(View.GONE);
                    findViewById(R.id.ll_all_select).setVisibility(View.VISIBLE);
                    mMode = AssetAdapter.CHOICE;
                    mTvEdit.setText(R.string.complete);
                } else {
                    findViewById(R.id.ll_add).setVisibility(View.VISIBLE);
                    findViewById(R.id.ll_all_select).setVisibility(View.GONE);
                    mMode = AssetAdapter.NORMAL;
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
                    selectSize = mAdapter.getmSelectListSize();
                    setDel_btn_text(selectSize);
                } else {
                    mAdapter.unSelect();
                    selectSize = mAdapter.getmSelectListSize();
                    setDel_btn_text(selectSize);
                }
                break;
            case R.id.btn_del:
                mAdapter.del();
                selectSize = 0;
                del_btn.setText("删除");
                mAssets.clear();
                mAssets.addAll(mAdapter.getAssets());
                if (mAssets.size() == 0) {
                    findViewById(R.id.ll_asset).setVisibility(View.GONE);
                    findViewById(R.id.v_divider).setVisibility(View.VISIBLE);
                    mTvEdit.performClick();
                }
                SpannableString spannableString = new SpannableString(getString(R.string.already_chosen) + getString(R.string.pending_inventory_assets) + mAssets.size() + getString(R.string.item));
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray_text)), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 8, spannableString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray_text)), spannableString.length() - 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvSelectSize.setText(spannableString);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.GET_ASSET_CODE && resultCode == RESULT_OK) {
            ArrayList<AssetBean> assets = (ArrayList<AssetBean>) data.getSerializableExtra(Constant.INTENT_EXTRA_KEY_ASSETS);
            if (assets != null && assets.size() > 0) {
                mAssets.addAll(assets);
            }
            if (mAssets.size() > 0) {
                mAdapter.changeDataSource(mAssets);
                findViewById(R.id.ll_asset).setVisibility(View.VISIBLE);
                findViewById(R.id.v_divider).setVisibility(View.GONE);
            } else {
                mAdapter.clear();
                findViewById(R.id.ll_asset).setVisibility(View.GONE);
                findViewById(R.id.v_divider).setVisibility(View.VISIBLE);
            }
            assetSize = mAssets.size();
            SpannableString spannableString = new SpannableString(getString(R.string.already_chosen) + getString(R.string.pending_inventory_assets) + assetSize + getString(R.string.item));
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray_text)), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 8, spannableString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_gray_text)), spannableString.length() - 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvSelectSize.setText(spannableString);
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
                Intent intent = new Intent(CreateInventoryActivity.this, InventoryListActivity.class);
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
            Intent intent = new Intent(CreateInventoryActivity.this, InventoryListActivity.class);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfirm(User user) {
        mUser = user;
        mTvInputUser.setText(user.getUser_name());
        mTvInputUser.setTextColor(getResources().getColor(R.color.black_text));
    }

    @Override
    public void onCancel() {
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
    public void onItemClick(int position) {
        if (mAdapter.getMode() == AssetAdapter.NORMAL) {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.BEDETAIL);
            intent.putExtra(Constant.INTENT_EXTRA_KEY_ASSER_ID, mAssets.get(position).getAsset_id());
            intent.putExtra(Constant.INTENT_IS_HISTORY_ASSER_ID, "0");
            intent.putExtra("webName", "资产详情");
            startActivity(intent);
        } else {
            mAdapter.changeSelect(position);
            mIsAllSelect = mAdapter.isAllSelect();
            mIvChoice.setImageResource(mIsAllSelect ? R.drawable.ic_select : R.drawable.ic_unchecked);
            selectSize = mAdapter.getmSelectListSize();
            setDel_btn_text(selectSize);
        }

    }

//    @Override
//    public void initUsers(ResultUsers resultUsers) {
//        mLlPicker.setVisibility(View.VISIBLE);
//        mLlPicker.setData(resultUsers.getResult());
//        mLlPicker.setOnSelectListener(this);
//    }

    @Override
    public void addInventoryResult(Bean resultBean) {
        mIsCreated = true;
        showSavedDialog();
    }

    @Override
    public void initUsers(List<User> user) {
        mLlPicker.setVisibility(View.VISIBLE);
        mLlPicker.setData(user);
        mLlPicker.setOnSelectListener(this);
    }

    private boolean checkParam() {
        if (TextUtils.isEmpty(mEtInputName.getText().toString())) {
            ToastUtils.toastMessage(this, R.string.inventory_name_notnull);
            return false;
        }
        if (TextUtils.isEmpty(mEtInputName.getText().toString())) {
            ToastUtils.toastMessage(this, R.string.inventory_name_length);
            return false;
        }
        if (mUser == null) {
            ToastUtils.toastMessage(this, R.string.user_not_null);
            return false;
        }
        if (mAssets.size() == 0) {
            ToastUtils.toastMessage(this, R.string.asset_not_null);
            return false;
        }
        return true;
    }

    private ArrayList<String> getAssetIds() {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < mAssets.size(); i++) {
            ids.add(mAssets.get(i).getAsset_id());
        }
        Log.e("CreateActivity", "getAssetIds: " + ids);
        return ids;
    }

    private void setDel_btn_text(int selectSize) {
        if (selectSize == 0) {
            del_btn.setText("删除");
        } else {
            del_btn.setText("删除(" + selectSize + ")");
        }
    }
}
