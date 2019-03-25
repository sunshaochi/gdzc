package com.gengcon.android.fixedassets.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.AssetBean;
import com.gengcon.android.fixedassets.util.DensityUtils;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AssetAdapter extends MyRecyclerView.Adapter<AssetAdapter.ViewHolder> implements View.OnClickListener {

    public static int NORMAL = 0x01;
    public static int CHOICE = 0x02;

    public boolean mIsSetPadding = false;

    private Context mContext;
    private OnItemClickListener mItemClickListener;
    private List<AssetBean> mAssets;
    private List<AssetBean> mSelectList;
    private int mMode = NORMAL;

    public AssetAdapter(Context context) {
        mContext = context;
        mAssets = new ArrayList<>();
    }

    public void addDataSource(List<AssetBean> assets) {
        if (assets != null && assets.size() > 0) {
            mAssets.addAll(assets);
            notifyDataSetChanged();
        }
    }

    public List<AssetBean> getAssets() {
        return mAssets;
    }

    public void changeDataSource(List<AssetBean> assets) {
        clear();
        if (assets != null && assets.size() > 0) {
            mAssets.addAll(assets);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        mAssets.clear();
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setMode(int mode) {
        mMode = mode;
        mSelectList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public boolean isAllSelect() {
        return mSelectList.size() == mAssets.size();
    }

    public void changeSelect(int position) {
        if (mSelectList.contains(mAssets.get(position))) {
            mSelectList.remove(mAssets.get(position));
        } else {
            mSelectList.add(mAssets.get(position));
        }
        notifyItemChanged(position);
    }

    public int getmSelectListSize() {
        if (mSelectList != null) {
            return mSelectList.size();
        }
        return 0;
    }

    public void allSelect() {
        mSelectList.clear();
        for (int i = 0; i < mAssets.size(); i++) {
            mSelectList.add(mAssets.get(i));
        }
        notifyDataSetChanged();
    }

    public void unSelect() {
        mSelectList.clear();
        notifyDataSetChanged();
    }

    public void del() {
        for (int i = 0; i < mSelectList.size(); i++) {
            mAssets.remove(mSelectList.get(i));
        }
        mSelectList.clear();
        notifyDataSetChanged();
    }

    public int getMode() {
        return mMode;
    }

    public void setPadding(boolean isPadding) {
        mIsSetPadding = isPadding;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_asset, parent, false);
        view.setOnClickListener(this);
        if (mIsSetPadding) {
            view.setPadding(DensityUtils.dp2px(mContext, 15), DensityUtils.dp2px(mContext, 10), DensityUtils.dp2px(mContext, 15), DensityUtils.dp2px(mContext, 10));
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AssetBean asset = mAssets.get(position);
        if (mMode == CHOICE) {
            if (mSelectList.contains(asset)) {
                holder.ivLeftChoice.setImageResource(R.drawable.ic_select);
            } else {
                holder.ivLeftChoice.setImageResource(R.drawable.ic_unchecked);
            }
            holder.ivLeftChoice.setVisibility(View.VISIBLE);
            holder.ivArrow.setVisibility(View.GONE);
        } else if (mMode == NORMAL) {
            holder.ivLeftChoice.setVisibility(View.GONE);
            holder.ivArrow.setVisibility(View.VISIBLE);
        }
        holder.tvName.setText(asset.getAsset_name());
        holder.tvId.setText(asset.getAsset_code());
        holder.tvStatus.setText(asset.getStatus_cn());
        holder.itemView.setTag(position);
        Glide.with(mContext).load(TextUtils.isEmpty(asset.getPhotourl()) ? asset.getPhotourl() : SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.IMG_URL, "") + "/" + asset.getPhotourl()).error(R.drawable.ic_default_img).placeholder(R.drawable.ic_default_img).fallback(R.drawable.ic_default_img).into(holder.ivIcon);
        switch (asset.getStatus()) {
            case AssetBean.IDEL:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_idel);
                break;
            case AssetBean.PEPAIR:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_perair);
                break;
            case AssetBean.IN_USE:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_in_use);
                break;
            case AssetBean.SCRAP:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_scrap);
                break;
            case AssetBean.LEND:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_lend);
                break;
            case AssetBean.SCRAP_AUDITING:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_scrap_auditing);
                break;
            case AssetBean.ALLOT_AUDITING:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_allot_auditing);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mAssets.size();
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) view.getTag());
        }
    }

    class ViewHolder extends MyRecyclerView.ViewHolder {

        TextView tvName, tvId, tvStatus;
        ImageView ivIcon, ivLeftChoice, ivArrow;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvId = view.findViewById(R.id.tv_id);
            ivIcon = view.findViewById(R.id.iv_icon);
            ivLeftChoice = view.findViewById(R.id.iv_left_choice);
            ivArrow = view.findViewById(R.id.iv_arrow);
            tvStatus = view.findViewById(R.id.tv_status);
        }
    }

}
