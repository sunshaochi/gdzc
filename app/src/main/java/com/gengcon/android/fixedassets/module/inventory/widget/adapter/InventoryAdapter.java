package com.gengcon.android.fixedassets.module.inventory.widget.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.ResultInventory;
import com.gengcon.android.fixedassets.common.ItemTouchListener;
import com.gengcon.android.fixedassets.module.greendao.InventoryBean;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ItemTouchListener mItemTouchListener;
    private List<InventoryBean> mInventorys;
    private long wpCount, ypCount;

    public InventoryAdapter(Context context) {
        mContext = context;
        mInventorys = new ArrayList<>();
    }

    public void setItemTouchListener(ItemTouchListener mItemTouchListener) {
        this.mItemTouchListener = mItemTouchListener;
    }

    public void addDataSource(List<InventoryBean> list) {
        mInventorys.clear();
        if (list != null && list.size() > 0) {
            mInventorys.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void changeDataSource(ResultInventory resultInventorys) {
        if (resultInventorys.getList() != null && resultInventorys.getList().size() > 0) {
            mInventorys.clear();
            mInventorys.addAll(resultInventorys.getList());
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_inventory, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final InventoryBean inventory = mInventorys.get(position);
        String date = inventory.getCreated_at();
        String useDate = date.substring(0, 10);
        holder.inventory_name.setText(inventory.getPd_name());
        holder.inventory_create_name.setText("创建人：" + inventory.getCreated_username());
        if (inventory.getStatus() != 3) {
            holder.py_layout.setVisibility(View.GONE);
            holder.zc_text.setText("已盘 ");
            holder.pk_text.setText("未盘 ");
        } else {
            holder.py_layout.setVisibility(View.VISIBLE);
            holder.zc_text.setText("正常 ");
            holder.pk_text.setText("盘亏 ");
            holder.py_num.setText(inventory.getPy_num() + "");
        }
        holder.pk_num.setText(inventory.getWp_num() + "");
        holder.zc_num.setText(inventory.getYp_num() + "");
        holder.inventory_create_time.setText(useDate);
        if (inventory.getStatus() == 1) {
            holder.inventory_status.setText(inventory.getStatus_cn());
            holder.inventory_status.setTextColor(mContext.getResources().getColor(R.color.blue));
            holder.inventory_status.setBackgroundResource(R.drawable.inventory_doing);
        } else if (inventory.getStatus() == 2) {
            holder.inventory_status.setText(inventory.getStatus_cn());
            holder.inventory_status.setTextColor(mContext.getResources().getColor(R.color.approval_reject));
            holder.inventory_status.setBackgroundResource(R.drawable.inventory_wait);
        } else if (inventory.getStatus() == 3) {
            holder.inventory_status.setText(inventory.getStatus_cn());
            holder.inventory_status.setTextColor(mContext.getResources().getColor(R.color.inventory_normal));
            holder.inventory_status.setBackgroundResource(R.drawable.inventory_finished);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemTouchListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInventorys.size();
    }

    @Override
    public void onClick(View view) {

    }

    public InventoryBean getItem(int i) {
        return mInventorys.get(i);
    }

    class ViewHolder extends MyRecyclerView.ViewHolder {

        TextView inventory_name, inventory_status, inventory_create_time, inventory_create_name, py_num, pk_num, zc_num;
        LinearLayout py_layout;
        TextView zc_text, pk_text;

        public ViewHolder(View view) {
            super(view);
            inventory_name = view.findViewById(R.id.inventory_name);
            inventory_status = view.findViewById(R.id.inventory_status);
            inventory_create_time = view.findViewById(R.id.inventory_create_time);
            inventory_create_name = view.findViewById(R.id.inventory_create_name);
            py_num = view.findViewById(R.id.py_num);
            pk_num = view.findViewById(R.id.pk_num);
            zc_num = view.findViewById(R.id.zc_num);
            zc_text = view.findViewById(R.id.zc_text);
            pk_text = view.findViewById(R.id.pk_text);
            py_layout = view.findViewById(R.id.py_layout);
        }
    }
}
