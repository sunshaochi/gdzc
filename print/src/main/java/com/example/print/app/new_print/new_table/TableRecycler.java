package com.example.print.app.new_print.new_table;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class TableRecycler extends RecyclerView {
    public TableRecycler(Context context) {
        super(context);
    }

    public TableRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TableRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }



}
