package com.gengcon.android.fixedassets.module.user.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.OrgBean;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaffManagerOrgAdapter extends RecyclerView.Adapter<StaffManagerOrgAdapter.ViewHolder> {

    private Context mContext;
    private List<OrgBean> orgBeans;
    private StaffManagerCallback staffManagerCallback;

    public StaffManagerOrgAdapter(Context context) {
        mContext = context;
        orgBeans = new ArrayList<>();
    }

    public void setCallBack(StaffManagerCallback callBack) {
        staffManagerCallback = callBack;
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
        holder.dataNameView.setText(orgBean.getOrg_name()+"("+orgBean.getEmployee_num()+")");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staffManagerCallback.clickItem(orgBean);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orgBeans.size();
    }

    class ViewHolder extends MyRecyclerView.ViewHolder {

        TextView dataNameView;

        public ViewHolder(View view) {
            super(view);
            dataNameView = view.findViewById(R.id.dataNameView);
        }
    }

    public interface StaffManagerCallback {
        void clickItem(OrgBean orgBean);
    }
}
