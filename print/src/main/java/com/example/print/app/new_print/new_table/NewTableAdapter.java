package com.example.print.app.new_print.new_table;


import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class NewTableAdapter extends RecyclerView.Adapter<NewTableAdapter.Holder> {

int  p=0;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TableTextView textView = new TableTextView(parent.getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            textView.setBackgroundColor(Color.RED);
            textView.setClickable(false);
            textView.setFocusable(false);
            textView.setLayoutParams(layoutParams);
            return new Holder(textView);
//        layoutParams.leftMargin = 10;
//        layoutParams.topMargin = 10;
//        layoutParams.bottomMargin = 10;
//        layoutParams.rightMargin = 10;

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.getItemView().setText("逍遥");
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView itemView;

        public Holder(TextView itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public TextView getItemView() {
            return itemView;
        }
    }

}

