package com.example.print.app.new_print.new_table;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TableItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;

    public TableItemDecoration() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        final GridLayoutManager.SpanSizeLookup lookup = layoutManager.getSpanSizeLookup();
        int spanCount = layoutManager.getSpanCount();

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (true) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                //画竖线
                int top = child.getTop() - params.topMargin - size;
                int bottom = child.getBottom() + params.bottomMargin + size;
                int left = child.getLeft() - params.leftMargin - size;
                int right = left + size;
                c.drawRect(left, top, right, bottom, mPaint);
                //画横线
                top = child.getTop() - params.topMargin - size;
                bottom = top + size;
                left = child.getLeft() - params.leftMargin - size;
                right = child.getRight() + params.rightMargin + size;
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }


    }


    private int size = 10;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int position = parent.getChildLayoutPosition(view);
        int spanCount = layoutManager.getSpanCount();
        //不是第一个的格子都设一个左边和底部的间距
        outRect.top = size;
        outRect.left = size;


    }
}
