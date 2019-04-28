package com.gengcon.android.fixedassets.module.addasset.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.ChooseUserBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseUserAdapter extends RecyclerView.Adapter<ChooseUserAdapter.ViewHolder> {

    private Context mContext;
    private List<ChooseUserBean> chooseUserBeans;
    private ChooseUserCallback chooseUserCallback;

    public ChooseUserAdapter(Context context) {
        mContext = context;
        chooseUserBeans = new ArrayList<>();
    }

    public void setCallBack(ChooseUserCallback callBack) {
        chooseUserCallback = callBack;
    }

    public void addDataSource(List<ChooseUserBean> chooseUserBean) {
        chooseUserBeans.clear();
        if (chooseUserBean != null && chooseUserBean.size() > 0) {
            chooseUserBeans.addAll(chooseUserBean);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_choose_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ChooseUserBean userBean = chooseUserBeans.get(position);
        holder.userNameView.setText(userBean.getEmp_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseUserCallback.clickItem(userBean.getId(), userBean.getEmp_name());
            }
        });
    }

    @Override
    public int getItemCount() {
        return chooseUserBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView userNameView;

        public ViewHolder(View view) {
            super(view);
            userNameView = view.findViewById(R.id.userNameView);
        }
    }

    public interface ChooseUserCallback {
        void clickItem(int userId, String userName);
    }
}
