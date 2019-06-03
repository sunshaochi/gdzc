package com.gengcon.android.fixedassets.module.addasset.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.ClassificationBean;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddAssetDataAdapter extends RecyclerView.Adapter<AddAssetDataAdapter.ViewHolder> {

    private Context mContext;
    private List<OrgBean> orgBeans;
    private List<ClassificationBean> classificationBeans;
    private AddAssetDataCallback addAssetDataCallback;
    private int addAssetType;

    public AddAssetDataAdapter(Context context, int addAssetType) {
        mContext = context;
        this.addAssetType = addAssetType;
        if (addAssetType == 1 || addAssetType == 3) {
            orgBeans = new ArrayList<>();
        } else if (addAssetType == 2) {
            classificationBeans = new ArrayList<>();
        }
    }

    public void setCallBack(AddAssetDataCallback callBack) {
        addAssetDataCallback = callBack;
    }

    public void addOrgDataSource(List<OrgBean> orgBean) {
        orgBeans.clear();
        if (orgBean != null && orgBean.size() > 0) {
            orgBeans.addAll(orgBean);
        }
        notifyDataSetChanged();
    }

    public void addClassificationDataSource(List<ClassificationBean> classificationBean) {
        classificationBeans.clear();
        if (classificationBean != null && classificationBean.size() > 0) {
            classificationBeans.addAll(classificationBean);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_asset_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (addAssetType == 1 || addAssetType == 3) {
            final OrgBean orgBean = orgBeans.get(position);
            holder.dataNameView.setText(orgBean.getOrg_name());
            holder.dataImg.setVisibility(View.VISIBLE);
            if (orgBean.getType() == 1) {
                holder.dataImg.setBackgroundResource(R.drawable.ic_company);
            } else {
                holder.dataImg.setBackgroundResource(R.drawable.ic_bumen);
            }
            final boolean hasChildren = orgBean.getChildren() != null && orgBean.getChildren().size() > 0;
            if (hasChildren) {
                holder.childrenLayout.setVisibility(View.VISIBLE);
            } else {
                holder.childrenLayout.setVisibility(View.GONE);
            }
            if (orgBean.isDisabled()) {
                holder.dataNameView.setTextColor(mContext.getResources().getColor(R.color.light_gray_text));
                holder.itemView.setEnabled(false);
            } else {
                holder.itemView.setEnabled(true);
                holder.dataNameView.setTextColor(mContext.getResources().getColor(R.color.black_text));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addAssetDataCallback.clickDataItem(orgBean.getId(), orgBean.getOrg_name());
                    }
                });
            }
            holder.childrenLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addAssetDataCallback.reloadOrg(orgBean.getChildren(), orgBean.getOrg_name());
                }
            });
        } else if (addAssetType == 2) {
            final ClassificationBean classificationBean = classificationBeans.get(position);
            holder.dataNameView.setText(classificationBean.getCustom_type_name());
            holder.dataImg.setVisibility(View.GONE);
            final boolean hasChildren = classificationBean.getChildren() != null && classificationBean.getChildren().size() > 0;
            if (hasChildren) {
                holder.childrenLayout.setVisibility(View.VISIBLE);
            } else {
                holder.childrenLayout.setVisibility(View.GONE);
            }
            if (classificationBean.isDisabled()) {
                holder.itemView.setEnabled(false);
                holder.dataNameView.setTextColor(mContext.getResources().getColor(R.color.light_gray_text));
            } else {
                holder.itemView.setEnabled(true);
                holder.dataNameView.setTextColor(mContext.getResources().getColor(R.color.black_text));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addAssetDataCallback.clickDataItem(classificationBean.getId(), classificationBean.getCustom_type_name());
                    }
                });
            }
            holder.childrenLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addAssetDataCallback.reloadClassification(classificationBean.getChildren(), classificationBean.getCustom_type_name());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (addAssetType == 1 || addAssetType == 3) {
            return orgBeans.size();
        } else if (addAssetType == 2) {
            return classificationBeans.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends MyRecyclerView.ViewHolder {

        TextView dataNameView;
        //        ImageView isReadImg;
        LinearLayout childrenLayout;
        ImageView dataImg;

        public ViewHolder(View view) {
            super(view);
            dataNameView = view.findViewById(R.id.dataNameView);
            childrenLayout = view.findViewById(R.id.childrenLayout);
            dataImg = view.findViewById(R.id.dataImg);
        }
    }

    public interface AddAssetDataCallback {
        void clickDataItem(int dataId, String name);

        void reloadOrg(List<OrgBean> orgBeans, String orgName);

        void reloadClassification(List<ClassificationBean> classificationBeans, String classificationName);
    }
}
