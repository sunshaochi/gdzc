package com.gengcon.android.fixedassets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.Approval;
import com.gengcon.android.fixedassets.bean.result.ResultApprovals;
import com.gengcon.android.fixedassets.view.ItemTouchListener;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ApprovalListAdapter extends RecyclerView.Adapter<ApprovalListAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ItemTouchListener mItemTouchListener;
    private List<Approval> approvals;
    private int type;

    public ApprovalListAdapter(Context context, int type) {
        mContext = context;
        this.type = type;
        approvals = new ArrayList<>();
    }

    public void setItemTouchListener(ItemTouchListener mItemTouchListener) {
        this.mItemTouchListener = mItemTouchListener;
    }

    public void addDataSource(ResultApprovals resultApprovals) {
        if (resultApprovals.getList() != null && resultApprovals.getList().size() > 0) {
            if (resultApprovals.getPage_count() == 0) {
                approvals.clear();
            }
            approvals.addAll(resultApprovals.getList());
            notifyDataSetChanged();
        }
    }

    public void changeDataSource(ResultApprovals resultApprovals) {
        if (resultApprovals.getList() != null && resultApprovals.getList().size() > 0) {
            approvals.clear();
            approvals.addAll(resultApprovals.getList());
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_approval_test, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Approval approval = approvals.get(position);
        if (type == 1) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemTouchListener.onItemClick(position);
                }
            });
        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemTouchListener.onItemClick(position);
                }
            });
        }
        holder.tvCreateName.setText(mContext.getString(R.string.apply_user) + "：" + approval.getCreated_username());
        holder.tvOrder.setText(approval.getDoc_no());
        holder.tvTime.setText(approval.getCreated_at());
        holder.tvName.setText(approval.getDoc_type_cn());
        holder.tvAssetNumber.setText("资产数量：" + approval.getAsset_num());
        holder.tvStatus.setText(approval.getStatus_cn());
        holder.tvStatus.setTextColor(mContext.getResources().getColor(approval.getStatus() == Approval.REJECT ? R.color.approval_reject : approval.getStatus() == Approval.AGREE ? R.color.approval_agree : R.color.approval_wait));
        holder.tvStatus.setBackgroundResource(approval.getStatus() == Approval.REJECT ? R.drawable.approval_reject : approval.getStatus() == Approval.AGREE ? R.drawable.approval_agree : R.drawable.approval_wait);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return approvals.size();
    }

    @Override
    public void onClick(View view) {
        if (mItemTouchListener != null) {
            mItemTouchListener.onItemClick((Integer) view.getTag());
        }
    }

    public Approval getItem(int i) {
        return approvals.get(i);
    }

    class ViewHolder extends MyRecyclerView.ViewHolder {

        TextView tvName, tvStatus, tvOrder, tvCreateName, tvTime, tvAssetNumber;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvStatus = view.findViewById(R.id.tv_status);
            tvOrder = view.findViewById(R.id.tv_order);
            tvCreateName = view.findViewById(R.id.tv_create_name);
            tvTime = view.findViewById(R.id.tv_time);
            tvAssetNumber = view.findViewById(R.id.tv_asset_number);
        }
    }
}
