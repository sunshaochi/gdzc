package com.example.print.app.new_print;


import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.example.print.app.BaseRecyclerViewAdapterHelper.BaseQuickAdapter;
import com.example.print.app.BaseRecyclerViewAdapterHelper.BaseViewHolder;
import com.example.print.app.R;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class ListAdapter extends BaseQuickAdapter<Function, BaseViewHolder> {

    public ListAdapter(List<Function> list) {
        super(R.layout.layout_listrecyclerview_item, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, final Function item) {
        TextView textView = helper.getView(R.id.tv_text);
        Drawable top = mContext.getResources().getDrawable(item.icon);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        textView.setText(item.name);
    }
}