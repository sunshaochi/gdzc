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
import androidx.recyclerview.widget.RecyclerView;

public class ApprovalAssetAdapter extends MyRecyclerView.Adapter<ApprovalAssetAdapter.ViewHolder> implements View.OnClickListener {

    public boolean mIsSetPadding = false;

    private Context mContext;
    private OnItemClickListener mItemClickListener;
    private List<AssetBean> mAssets;

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private View mHeaderView;

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

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
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

        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asset_approval, parent, false);
        view.setOnClickListener(this);
        if (mIsSetPadding) {
            view.setPadding(DensityUtils.dp2px(mContext, 15), DensityUtils.dp2px(mContext, 10), DensityUtils.dp2px(mContext, 15), DensityUtils.dp2px(mContext, 10));
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        final int pos = getRealPosition(holder);
        if (holder instanceof ApprovalAssetAdapter.ViewHolder) {
            AssetBean asset = mAssets.get(pos);
            holder.tvStatus.setVisibility(View.GONE);
            holder.tvName.setText(asset.getAsset_name());
            holder.tvId.setText(asset.getAsset_code());
            holder.tvStatus.setText(asset.getStatus_cn());
            holder.itemView.setTag(position);
            Glide.with(mContext).load(TextUtils.isEmpty(asset.getPhotourl()) ? asset.getPhotourl() : SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.IMG_URL, "") + "/" + asset.getPhotourl()).error(R.drawable.ic_default_img).placeholder(R.drawable.ic_default_img).fallback(R.drawable.ic_default_img).into(holder.ivIcon);
//            ((ViewHolder) holder).text.setText(data);
            if (mItemClickListener == null) return;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(pos);
                }
            });
        }

    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mAssets.size() : mAssets.size() + 1;
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) view.getTag());
        }
    }

    class ViewHolder extends MyRecyclerView.ViewHolder {

        TextView tvName, tvId, tvStatus;
        ImageView ivIcon, ivLeftChoice;

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
