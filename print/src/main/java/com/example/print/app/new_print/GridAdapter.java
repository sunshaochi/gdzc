package com.example.print.app.new_print;


import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.example.print.app.BaseRecyclerViewAdapterHelper.BaseQuickAdapter;
import com.example.print.app.BaseRecyclerViewAdapterHelper.BaseViewHolder;
import com.example.print.app.R;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class GridAdapter extends BaseQuickAdapter<Icon, BaseViewHolder> implements View.OnClickListener {

    public GridAdapter(List<Icon> list) {
        super(R.layout.layout_gridrecyclerview_item, list);
    }
private OnItemViewClickListener onItemViewClickListener;

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Icon item) {
        TextView textView = helper.getView(R.id.tv_text);
        Drawable top = mContext.getResources().getDrawable(item.icon);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        textView.setText(item.name);
        textView.setTag(item.name);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    if(onItemViewClickListener!=null){
        onItemViewClickListener.onItemViewClick((String) v.getTag());
    }
    }

    public interface OnItemViewClickListener {
        void onItemViewClick(String tag);
    }
}
