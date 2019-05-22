package com.gengcon.android.fixedassets.module.user.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrgSettingSecondAdapter extends RecyclerView.Adapter<OrgSettingSecondAdapter.ViewHolder> {

    private Context mContext;
    private List<OrgBean> orgBeans;
    private OrgSettingSecondCallback orgSettingSecondCallback;

    public OrgSettingSecondAdapter(Context context) {
        mContext = context;
        orgBeans = new ArrayList<>();
    }

    public void setCallBack(OrgSettingSecondCallback callBack) {
        orgSettingSecondCallback = callBack;
    }

    public void addDataSource(List<OrgBean> orgBean) {
        orgBeans.clear();
        if (orgBean != null && orgBean.size() > 0) {
            orgBeans.addAll(orgBean);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_org_setting_second, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final OrgBean orgBean = orgBeans.get(position);
        if (orgBean.getType() == 1) {
            holder.dataImg.setBackgroundResource(R.drawable.ic_company);
        } else if (orgBean.getType() == 2) {
            holder.dataImg.setBackgroundResource(R.drawable.ic_bumen);
        }
        holder.dataNameView.setText(orgBean.getOrg_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orgSettingSecondCallback.clickItem(orgBean);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orgBeans.size();
    }

    class ViewHolder extends MyRecyclerView.ViewHolder {

        TextView dataNameView;

        ImageView dataImg;

        public ViewHolder(View view) {
            super(view);
            dataNameView = view.findViewById(R.id.dataNameView);
            dataImg = view.findViewById(R.id.dataImg);
        }
    }

    public interface OrgSettingSecondCallback {
        void clickItem(OrgBean orgBean);
    }
}
