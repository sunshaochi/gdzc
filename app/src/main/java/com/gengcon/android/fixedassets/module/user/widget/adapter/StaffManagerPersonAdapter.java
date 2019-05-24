package com.gengcon.android.fixedassets.module.user.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.EmpBean;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class StaffManagerPersonAdapter extends DelegateAdapter.Adapter<StaffManagerPersonAdapter.ViewHolder> {

    private Context mContext;
    private List<EmpBean> empBeans;
    private PersonDetailCallback personDetailCallback;
    private LayoutHelper layoutHelper;

    public StaffManagerPersonAdapter(Context context, LayoutHelper layoutHelper) {
        mContext = context;
        empBeans = new ArrayList<>();
        this.layoutHelper = layoutHelper;
    }

    public void setCallBack(PersonDetailCallback callBack) {
        personDetailCallback = callBack;
    }

    public void addDataSource(List<EmpBean> empBean) {
        empBeans.clear();
        if (empBean != null && empBean.size() > 0) {
            empBeans.addAll(empBean);
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

        final EmpBean empBean = empBeans.get(position);
        holder.dataNameView.setText(empBean.getEmp_name());
        holder.dataImg.setBackgroundResource(R.drawable.ic_emp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personDetailCallback.clickPersonItem(empBean.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return empBeans.size();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
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

    public interface PersonDetailCallback {
        void clickPersonItem(int empId);
    }
}
