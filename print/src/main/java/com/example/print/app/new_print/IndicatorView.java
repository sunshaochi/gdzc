package com.example.print.app.new_print;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IndicatorView extends LinearLayout {
    private List<IndicatorItem> arrayList = new ArrayList<>();


    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIndicatorCount(int count) {
        for (int i = 0; i < count; i++) {
            IndicatorItem indicatorItem = new IndicatorItem(getContext());
            LayoutParams layoutParams = new LayoutParams(20, 20);
            layoutParams.setMargins(10, 0, 10, 0);
            indicatorItem.setLayoutParams(layoutParams);
            addView(indicatorItem);
            arrayList.add(indicatorItem);
        }
//        new android.os.Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                requestLayout();
//                invalidate();
//            }
//        },100);
    }

    public void setIndicator(int pos) {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(pos).setSelect(true);
            if (pos != i) {
                arrayList.get(i).setSelect(false);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
