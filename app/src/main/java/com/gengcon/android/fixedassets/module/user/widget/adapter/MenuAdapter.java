package com.gengcon.android.fixedassets.module.user.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context mContext;
    private List<String> menuNames;
    private MenuCallback menuCallback;

    public MenuAdapter(Context context) {
        mContext = context;
        menuNames = new ArrayList<>();
    }

    public void setCallBack(MenuCallback callBack) {
        menuCallback = callBack;
    }

    public void addDataSource(List<String> menuName) {
        menuNames.clear();
        if (menuName != null && menuName.size() > 0) {
            menuNames.addAll(menuName);
        }
        notifyDataSetChanged();
    }

    public void changeDataSource(List<String> menuName) {
        menuNames.clear();
        if (menuName != null && menuName.size() > 0) {
            menuNames.addAll(menuName);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, final int position) {
        final String menuName = menuNames.get(position);
        holder.menuNameView.requestLayout();
        holder.menuNameView.setText(menuName);
        if (position == menuNames.size() - 1) {
            holder.diverView.setVisibility(View.GONE);
        } else {
            holder.diverView.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuCallback.clickMenu(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuNames.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView menuNameView;
        View diverView;

        public MenuViewHolder(View view) {
            super(view);
            menuNameView = view.findViewById(R.id.menuNameView);
            diverView = view.findViewById(R.id.diverView);
        }
    }

    public interface MenuCallback {
        void clickMenu(int position);
    }
}
