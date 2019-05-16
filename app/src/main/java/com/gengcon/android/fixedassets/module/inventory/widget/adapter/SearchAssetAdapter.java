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
import com.gengcon.android.fixedassets.module.greendao.AssetBean;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAssetAdapter extends RecyclerView.Adapter<SearchAssetAdapter.ViewHolder> {

    private Context mContext;
    private List<AssetBean> assetBeans;
    private SearchAssetCallback searchAssetCallback;

    public SearchAssetAdapter(Context context) {
        mContext = context;
        assetBeans = new ArrayList<>();
    }

    public void setCallBack(SearchAssetCallback callBack) {
        searchAssetCallback = callBack;
    }

    public void addDataSource(List<AssetBean> assetBean) {
        assetBeans.clear();
        if (assetBean != null && assetBean.size() > 0) {
            assetBeans.addAll(assetBean);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_asset, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final AssetBean assetBean = assetBeans.get(position);
        if (assetBean.getPd_status() == 1) {
            holder.inventoryButton.setText("确认盘点");
            holder.inventoryButton.setEnabled(true);
            holder.inventoryButton.setTextColor(mContext.getResources().getColor(R.color.gray_text));
            holder.inventoryButton.setBackgroundResource(R.drawable.bg_asset_isinventory);
        } else {
            holder.inventoryButton.setText("已盘");
            holder.inventoryButton.setEnabled(false);
            holder.inventoryButton.setTextColor(mContext.getResources().getColor(R.color.light_gray_text));
            holder.inventoryButton.setBackgroundResource(R.drawable.bg_asset_noinventory);
        }
        holder.tvName.setText(assetBean.getAsset_name());
        holder.tvId.setText(assetBean.getAsset_code());
//        holder.tvStatus.setText(asset.getStatus());
        holder.itemView.setTag(position);
//        Glide.with(mContext).
//                load(TextUtils.isEmpty(assetBean.getPhotourl()) ? assetBean.getPhotourl() : SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.IMG_URL, "") + "/" + assetBean.getPhotourl())
//                .error(R.drawable.ic_default_img)
//                .placeholder(R.drawable.ic_default_img)
//                .fallback(R.drawable.ic_default_img)
//                .into(holder.ivIcon);
        Glide.with(mContext).load(TextUtils.isEmpty(assetBean.getPhotourl()) ? assetBean.getPhotourl() : SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.IMG_URL, "") + "/" + assetBean.getPhotourl())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_default_img)
                        .placeholder(R.drawable.ic_default_img)
                        .fallback(R.drawable.ic_default_img)).into(holder.ivIcon);
        switch (assetBean.getStatus()) {
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
        holder.inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAssetCallback.clickInventoryButton(assetBean.getAsset_id());
                holder.inventoryButton.setText("已盘");
                holder.inventoryButton.setEnabled(false);
                holder.inventoryButton.setTextColor(mContext.getResources().getColor(R.color.light_gray_text));
                holder.inventoryButton.setBackgroundResource(R.drawable.bg_asset_noinventory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return assetBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvId, tvStatus;
        ImageView ivIcon, ivLeftChoice, ivArrow;
        TextView inventoryButton;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvId = view.findViewById(R.id.tv_id);
            ivIcon = view.findViewById(R.id.iv_icon);
            ivLeftChoice = view.findViewById(R.id.iv_left_choice);
            ivArrow = view.findViewById(R.id.iv_arrow);
            tvStatus = view.findViewById(R.id.tv_status);
            inventoryButton = view.findViewById(R.id.inventoryButton);
        }
    }

    public interface SearchAssetCallback {
        void clickItem();

        void clickInventoryButton(String asset_id);
    }
}
