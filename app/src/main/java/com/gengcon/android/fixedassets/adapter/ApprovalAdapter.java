package com.gengcon.android.fixedassets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.ApprovalListBean;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ApprovalAdapter extends RecyclerView.Adapter<ApprovalAdapter.ViewHolder> {

    private Context mContext;
    private List<ApprovalListBean.ListBean> approvalList;
    private ApprovalCallback approvalCallback;

    public ApprovalAdapter(Context context) {
        mContext = context;
        approvalList = new ArrayList<>();
    }

    public void setItemTouchListener(ApprovalCallback approvalCallback) {
        this.approvalCallback = approvalCallback;
    }

    public void addDataSource(List<ApprovalListBean.ListBean> approval) {
        approvalList.clear();
        if (approval != null && approval.size() > 0) {
            approvalList.addAll(approval);
        }
        notifyDataSetChanged();
    }

    public void addMoreDataSource(List<ApprovalListBean.ListBean> approval) {
        if (approval != null && approval.size() > 0) {
            approvalList.addAll(approval);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_approval, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ApprovalListBean.ListBean approval = approvalList.get(position);
        String time = approval.getCreated_at();
        String yTime = time.substring(0, 4);
        holder.yTimeView.setText(yTime);
        String mTime = time.substring(5, time.length());
        holder.mTimeView.setText(mTime);
        holder.typeView.setText("处理类型：" + approval.getDoc_type_cn());
        holder.nameView.setText("处理人：" + approval.getCreated_username());
        holder.remarksView.setText("备注：" + approval.getDoc_remarks());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approvalCallback.clickApproval(approval.getDoc_type(), approval.getDoc_no());
            }
        });
    }

    @Override
    public int getItemCount() {
        return approvalList.size();
    }

    class ViewHolder extends MyRecyclerView.ViewHolder {

        TextView mTimeView, yTimeView, typeView, nameView, remarksView;

        public ViewHolder(View view) {
            super(view);
            mTimeView = view.findViewById(R.id.mTimeView);
            yTimeView = view.findViewById(R.id.yTimeView);
            typeView = view.findViewById(R.id.typeView);
            nameView = view.findViewById(R.id.nameView);
            remarksView = view.findViewById(R.id.remarksView);
        }
    }

    public interface ApprovalCallback {
        void clickApproval(int approvalType, String approvalId);
    }
}
