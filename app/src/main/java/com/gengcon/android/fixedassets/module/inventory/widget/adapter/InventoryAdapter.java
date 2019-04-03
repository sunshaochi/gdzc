package com.gengcon.android.fixedassets.module.inventory.widget.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeItemLayout;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.Inventory;
import com.gengcon.android.fixedassets.bean.result.ResultInventorys;
import com.gengcon.android.fixedassets.common.ItemTouchListener;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ItemTouchListener mItemTouchListener;
    private List<Inventory> mInventorys;
    private int type;
    private boolean can_edit;
    private boolean can_del;
    private boolean noData;

    public InventoryAdapter(Context context, int type, boolean can_edit, boolean can_del) {
        mContext = context;
        this.type = type;
        this.can_edit = can_edit;
        this.can_del = can_del;
        mInventorys = new ArrayList<>();
    }

    public void setItemTouchListener(ItemTouchListener mItemTouchListener) {
        this.mItemTouchListener = mItemTouchListener;
    }

    public void addDataSource(ResultInventorys resultInventorys) {
        if (resultInventorys.getList() != null && resultInventorys.getList().size() > 0) {
            if (resultInventorys.getPage_count() == 0) {
                mInventorys.clear();
            }
            mInventorys.addAll(resultInventorys.getList());
            notifyDataSetChanged();
        }
    }

    public void removeAsset(int position) {
        if (position == RecyclerView.NO_POSITION) {
            return;
        } else if (position >= 0 && position < mInventorys.size()) {
            mInventorys.remove(position);
            if (mInventorys.size() == 0) {
                noData = true;
            } else {
                noData = false;
            }
            notifyItemRemoved(position);
        }
        notifyDataSetChanged();

    }

    public boolean isNoData() {
        return noData;
    }

    public void changeDataSource(ResultInventorys resultInventorys) {
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
        if (type == 1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_inventory, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_create_menu, parent, false);
        }
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Inventory inventory = mInventorys.get(position);
        boolean canEdit = inventory.getCan_edit() == 1 ? true : false;
        if (holder.swipeItemLayout != null) {
            holder.swipeItemLayout.setSwipeEnable(canEdit);
            if (can_edit) {
                holder.editTextView.setVisibility(View.VISIBLE);
            } else {
                holder.editTextView.setVisibility(View.GONE);
            }
            if (can_del) {
                holder.deleteTextView.setVisibility(View.VISIBLE);
            } else {
                holder.deleteTextView.setVisibility(View.GONE);
            }
        }
        if (type == 1) {
            holder.tvCreateName.setText(mContext.getString(R.string.create_user) + "：" + inventory.getCreator_name());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemTouchListener.onItemClick(position);
                }
            });
        } else {
            holder.tvCreateName.setText(mContext.getString(R.string.do_user) + "：" + inventory.getAllot_username());
            if (canEdit) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (inventory.getAllot_username().equals(inventory.getCreator_name())) {
                            mItemTouchListener.onItemClick(position);
                        } else {
                            mItemTouchListener.onEditMenuClick(inventory.getInv_no());
                        }
                    }
                });
                if (holder.editTextView != null) {
                    holder.editTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemTouchListener.onEditMenuClick(inventory.getInv_no());
                            holder.swipeItemLayout.close();
                        }
                    });
                }
                if (holder.deleteTextView != null) {
                    holder.deleteTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemTouchListener.onDeleteMenuClick(inventory.getInv_no(), position);
                            holder.swipeItemLayout.close();
                        }
                    });
                }
            } else {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemTouchListener.onItemClick(position);
                    }
                });
            }
        }

        holder.tvCreateTime.setText(mContext.getString(R.string.create_time) + "：" + inventory.getCreated_at());
        holder.tvName.setText(inventory.getInv_name());
        holder.tvStatus.setText(inventory.getStatus_cn());
        holder.tvStatus.setTextColor(mContext.getResources().getColor(inventory.getStatus() == Inventory.ERROR ? R.color.inventory_error : inventory.getStatus() == Inventory.NORMAL ? R.color.inventory_normal : R.color.inventory_not));
        holder.tvStatus.setBackgroundResource(inventory.getStatus() == Inventory.ERROR ? R.drawable.inventory_error : inventory.getStatus() == Inventory.NORMAL ? R.drawable.inventory_normal : R.drawable.inventory_not);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mInventorys.size();
    }

    @Override
    public void onClick(View view) {
        if (mItemTouchListener != null) {
            mItemTouchListener.onItemClick((Integer) view.getTag());
        }
    }

    public Inventory getItem(int i) {
        return mInventorys.get(i);
    }

    class ViewHolder extends MyRecyclerView.ViewHolder {

        TextView tvName, tvStatus, tvCreateTime, tvCreateName, editTextView, deleteTextView;
        SwipeItemLayout swipeItemLayout;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvStatus = view.findViewById(R.id.tv_status);
            tvCreateTime = view.findViewById(R.id.tv_create_time);
            tvCreateName = view.findViewById(R.id.tv_create_name);
            editTextView = view.findViewById(R.id.edit_menu);
            deleteTextView = view.findViewById(R.id.delete_menu);
            swipeItemLayout = view.findViewById(R.id.swipe_layout);
        }
    }
}
