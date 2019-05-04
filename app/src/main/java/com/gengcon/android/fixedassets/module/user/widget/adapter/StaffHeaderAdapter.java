package com.gengcon.android.fixedassets.module.user.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaffHeaderAdapter extends RecyclerView.Adapter<StaffHeaderAdapter.HeaderViewHolder> {

    private Context mContext;
    private List<String> orgNames;
    private List<Integer> orgIds;
    private HeaderCallback headerCallback;

    public StaffHeaderAdapter(Context context) {
        mContext = context;
        orgNames = new ArrayList<>();
        orgIds = new ArrayList<>();
    }

    public void setCallBack(HeaderCallback callBack) {
        headerCallback = callBack;
    }

    public void addDataSource(List<String> orgName, List<Integer> id) {
        if (orgName != null && orgName.size() > 0) {
            orgNames.addAll(orgName);
        }
        if (id != null && id.size() > 0) {
            orgIds.addAll(id);
        }
        notifyDataSetChanged();
    }

    public void changeDataSource(List<String> orgName, List<Integer> id) {
        orgNames.clear();
        orgIds.clear();
        if (orgName != null && orgName.size() > 0) {
            orgNames.addAll(orgName);
        }
        if (id != null && id.size() > 0) {
            orgIds.addAll(id);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewHolder holder, final int position) {
        final String orgName = orgNames.get(position);
        holder.headerNameView.requestLayout();
        holder.headerNameView.setText(orgName);
        int index = orgNames.size() - 1;
        if (position == index) {
            holder.headerNameView.setTextColor(mContext.getResources().getColor(R.color.black_text));
            holder.headerNameView.setEnabled(false);
        } else {
            holder.headerNameView.setTextColor(mContext.getResources().getColor(R.color.blue));
            holder.headerNameView.setEnabled(true);
        }
        if (position == 0) {
            holder.headerImageView.setVisibility(View.GONE);
        } else {
            holder.headerImageView.setVisibility(View.VISIBLE);
        }
        holder.headerNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> names = new ArrayList<>();
                List<Integer> ids = new ArrayList<>();
                for (int i = 0; i < position + 1; i++) {
                    names.add(orgNames.get(i));
                    ids.add(orgIds.get(i));
                }
                headerCallback.clickHeader(names, ids, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orgNames.size();
    }

    class HeaderViewHolder extends MyRecyclerView.ViewHolder {

        TextView headerNameView;
        //        ImageView isReadImg;
        ImageView headerImageView;

        public HeaderViewHolder(View view) {
            super(view);
            headerNameView = view.findViewById(R.id.headerNameView);
            headerImageView = view.findViewById(R.id.headerImageView);
        }
    }

    public interface HeaderCallback {
        void clickHeader(List<String> headerNames, List<Integer> ids, int position);
    }
}
