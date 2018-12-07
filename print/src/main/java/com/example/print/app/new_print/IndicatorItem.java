package com.example.print.app.new_print;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import com.example.print.app.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class IndicatorItem extends View {
    private Paint paint;

    public IndicatorItem(Context context) {
        super(context);
        init();
    }


    public IndicatorItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndicatorItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#32000000"));
    }

    public void setSelect(boolean isSelect) {
        if (isSelect) {
            paint.setColor((getResources().getColor(R.color.toolbar_bg)));
        } else {
            paint.setColor(Color.parseColor("#32000000"));
        }
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getHeight() / 2, paint);
    }
}
