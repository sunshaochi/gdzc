package com.gengcon.android.fixedassets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.MessageBean;
import com.gengcon.android.fixedassets.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;
    private List<MessageBean.ListBean> messages;
    private MessageCallback messageCallback;

    public MessageAdapter(Context context) {
        mContext = context;
        messages = new ArrayList<>();
    }

    public void setCallBack(MessageCallback callBack) {
        messageCallback = callBack;
    }

    public void addDataSource(List<MessageBean.ListBean> message) {
        messages.clear();
        if (message != null && message.size() > 0) {
            messages.addAll(message);
        }
        notifyDataSetChanged();
    }

    public void addMoreDataSource(List<MessageBean.ListBean> message) {
        if (message != null && message.size() > 0) {
            messages.addAll(message);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final MessageBean.ListBean message = messages.get(position);

        holder.timeText.setText(message.getPush_at());
        holder.noticeName.setText(message.getNotice_name());
        holder.messageTitle.setText(message.getTitle());
        holder.messageContent.setText(message.getOutline());
//        holder.isReadImg.setBackgroundResource(message.getIs_read().equals("0") ? R.drawable.bg_message_unread : R.drawable.bg_message_read);
        if (message.getPush_model() == 2) {
            holder.hasDataLayout.setVisibility(View.VISIBLE);
            holder.messageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    messageCallback.clickMessage(message.getId());
                }
            });
        } else {
            holder.hasDataLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewHolder extends MyRecyclerView.ViewHolder {

        TextView timeText, noticeName, messageTitle, messageContent;
        //        ImageView isReadImg;
        LinearLayout messageLayout, hasDataLayout;

        public ViewHolder(View view) {
            super(view);
            timeText = view.findViewById(R.id.timeText);
            noticeName = view.findViewById(R.id.noticeName);
            messageTitle = view.findViewById(R.id.messageTitle);
            messageContent = view.findViewById(R.id.messageContent);
//            isReadImg = view.findViewById(R.id.isReadImg);
            messageLayout = view.findViewById(R.id.messageLayout);
            hasDataLayout = view.findViewById(R.id.hasDataLayout);
        }
    }

    public interface MessageCallback {
        void clickMessage(int msgId);
    }
}
