package com.gengcon.android.fixedassets.module.inventory.widget.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.Asset;
import com.gengcon.android.fixedassets.common.OnItemClickListener;
import com.gengcon.android.fixedassets.module.greendao.AssetBean;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class InventoryAssetAdapter extends MyRecyclerView.Adapter<InventoryAssetAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private OnItemClickListener mItemClickListener;
    private List<AssetBean> mAssets;

    public InventoryAssetAdapter(Context context) {
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

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_inventory_asset, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AssetBean asset = mAssets.get(position);
        holder.tvName.setText(asset.getAsset_name());
        holder.tvId.setText(asset.getAsset_code());
//        holder.tvStatus.setText(asset.getStatus());
        holder.itemView.setTag(position);
//        Glide.with(mContext).load(TextUtils.isEmpty(asset.getPhotourl()) ? asset.getPhotourl() : SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.IMG_URL, "") + "/" + asset.getPhotourl()).error(R.drawable.ic_default_img).placeholder(R.drawable.ic_default_img).fallback(R.drawable.ic_default_img).into(holder.ivIcon);
        Glide.with(mContext).load(TextUtils.isEmpty(asset.getPhotourl()) ? asset.getPhotourl() : SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.IMG_URL, "") + "/" + asset.getPhotourl()).apply(new RequestOptions().error(R.drawable.ic_default_img).placeholder(R.drawable.ic_default_img).fallback(R.drawable.ic_default_img)).into(holder.ivIcon);

        switch (asset.getStatus()) {
            case Asset.IDEL:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_idel);
                break;
            case Asset.PEPAIR:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_perair);
                break;
            case Asset.IN_USE:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_in_use);
                break;
            case Asset.SCRAP:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_scrap);
                break;
            case Asset.LEND:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_lend);
                break;
            case Asset.SCRAP_AUDITING:
                holder.tvStatus.setBackgroundResource(R.color.asset_status_scrap_auditing);
                break;
            case Asset.ALLOT_AUDITING:
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