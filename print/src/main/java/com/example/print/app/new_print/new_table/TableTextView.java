package com.example.print.app.new_print.new_table;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;

public class TableTextView extends AppCompatTextView {
    public TableTextView(Context context) {
        super(context);
    }

    public TableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return false;
    }
}
