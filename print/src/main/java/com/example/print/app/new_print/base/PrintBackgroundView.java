package com.example.print.app.new_print.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.example.print.app.R;
import com.example.print.app.util.TranslateUtils;

/**
 * 此类自定义父容器的线条和写入高宽
 */
public class PrintBackgroundView extends RelativeLayout {

    private Paint linePaint;
    private float templateWidth;
    private float templateHeight;
    private int childHeight;
    private int childWidth;
    private Paint textPaint;
    private int childMargin;

    public PrintBackgroundView(Context context) {
        this(context, null);
    }

    public PrintBackgroundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public PrintBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        linePaint = new Paint();
        linePaint.setStrokeWidth(1);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(getResources().getColor(R.color.gray_2));
        textPaint = new Paint();
        textPaint.setStrokeWidth(1);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(getResources().getColor(R.color.gray_2));

        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 0) {
            templateWidth = TranslateUtils.getMTemplateWidth();
            templateHeight = TranslateUtils.getMTemplateHeight();
            childMargin = ((LayoutParams) getChildAt(0).getLayoutParams()).topMargin;
            childHeight = getChildAt(0).getMeasuredHeight();
            childWidth = getChildAt(0).getMeasuredWidth();
            textPaint.setTextSize(childMargin / 2f);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (templateWidth <= 0 || templateHeight <= 0) {
            return;
        }
        float startX = childMargin;
        float endX = childMargin / 2f;
        float endX2 = childMargin / 4f * 3f;
        float widthSum = childWidth / templateWidth;
        float heightSum = childHeight / templateHeight;
        for (int i = 0; i < templateWidth + 1; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(childMargin + widthSum * i, startX, childMargin + widthSum * i, endX, linePaint);
            } else {
                canvas.drawLine(childMargin + widthSum * i, startX, childMargin + widthSum * i, endX2, linePaint);
            }
            if (i % 10 == 0) {
                canvas.drawText(String.valueOf(i), 0, String.valueOf(i).length(), childMargin + widthSum * i, endX - 3, textPaint);
            }
        }
        canvas.save();
        canvas.rotate(-90);
        for (int i = 0; i < templateHeight + 1; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(-childMargin - heightSum * i, startX, -childMargin - heightSum * i, endX, linePaint);
            } else {
                canvas.drawLine(-childMargin - heightSum * i, startX, -childMargin - heightSum * i, endX2, linePaint);
            }
            if (i % 10 == 0) {
                canvas.drawText(String.valueOf(i), 0, String.valueOf(i).length(), -childMargin - widthSum * i, endX - 3, textPaint);
            }

        }
        canvas.restore();
    }


}
