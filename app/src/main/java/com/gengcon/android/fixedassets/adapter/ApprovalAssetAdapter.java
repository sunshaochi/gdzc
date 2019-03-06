package com.gengcon.android.fixedassets.adapter;

import android.content.Context;
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

import androidx.annotation.NonNull;

public class ApprovalAssetAdapter extends MyRecyclerView.Adapter<ApprovalAssetAdapter.ViewHolder> implements View.OnClickListener {

    public static int NORMAL = 0x01;
    public static int CHOICE = 0x02;

    public boolean mIsSetPadding = false;

    private Context mContext;
    private OnItemClickListener mItemClickListener;
    private List<AssetBean> mAssets;
    private int mMode = NORMAL;

    public ApprovalAssetAdapter(Context context) {
        mContext = context;
        mAssets = new ArrayList<>();
    }

    public void addDataSource(List<AssetBean> assets) {
        mAssets.clear();
        if (assets != null && assets.size() > 0) {
            mAssets.addAll(assets);
        }
        notifyDataSetChanged();
    }

    public void addMoreDataSource(List<AssetBean> assets) {
        if (assets != null && assets.size() > 0) {
            mAssets.addAll(assets);
        }
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
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
        holder.tvName.setText(asset.getAsset_name());
        holder.tvId.setText(asset.getAsset_code());
        holder.tvStatus.setText(asset.getStatus_cn());
        holder.itemView.setTag(position);
        Glide.with(mContext).load(TextUtils.isEmpty(asset.getPhotourl()) ? asset.getPhotourl() : SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.IMG_URL, "") + "/" + asset.getPhotourl()).error(R.drawable.ic_default_img).placeholder(R.drawable.ic_default_img).fallback(R.drawable.ic_default_img).into(holder.ivIcon);
        switch (asset.getStatus()) {
            case AssetBean.IDEL:
                holder.tvStatus.setText(R.string.asset_status_idel);
                holder.tvStatus.setBackgroundResource(R.color.asset_status_idel);
                break;
            case AssetBean.PEPAIR:
                holder.tvStatus.setText(R.string.asset_status_perair);
                holder.tvStatus.setBackgroundResource(R.color.asset_status_perair);
                break;
            case AssetBean.IN_USE:
                holder.tvStatus.setText(R.string.asset_status_in_uer);
                holder.tvStatus.setBackgroundResource(R.color.asset_status_in_use);
                break;
            case AssetBean.SCRAP:
                holder.tvStatus.setText(R.string.asset_status_scrap);
                holder.tvStatus.setBackgroundResource(R.color.asset_status_scrap);
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
            tvStatus = view.findViewById(R.id.tv_status);
        }
    }

}
