package com.example.print.app.new_print.shape.Rect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.example.print.app.new_print.base.BaseItemView;
import com.example.print.app.new_print.base.IBackgroundLayer;
import com.example.print.app.new_print.module.BaseItemModule;
import com.example.print.app.new_print.shape.LineItem;
import com.example.print.app.new_print.shape.LineItemImpl;
import com.example.print.app.util.TranslateUtils;

public class RoundRectView extends BaseItemView implements LineItem, IBackgroundLayer.SecondLayer {
    private RectF rectF;
    private Paint linePaint;
    private LineItem item;
    private int type = 3;
    private int strokeWidth = 10;


    public RoundRectView(Context context) {
        super(context);
        setDrawDottedLine(false);
        setDrawBottomBitmap(true);
        rectF = new RectF();
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(0xff000000);
        linePaint.setStrokeWidth(strokeWidth);
        linePaint.setStyle(Paint.Style.STROKE);
        item = new LineItemImpl(linePaint, this);
    }


    public static RoundRectView create(Context context, int width, int height) {
        RoundRectView roundRectView = new RoundRectView(context);
        roundRectView.setSelected(true);
        LayoutParams layoutParams = new LayoutParams(width, height);
        roundRectView.setLayoutParams(layoutParams);
        return roundRectView;
    }


    @Override
    public BaseItemView copy() {
        RoundRectView copy = (RoundRectView) super.copy();
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(getWidth(), getHeight());
        layoutParams.topMargin = getTop() + 10;
        layoutParams.leftMargin = getLeft() + 10;
        copy.setLayoutParams(layoutParams);
        copy.setLineType(item.getLinType());
        copy.setLineSize(item.getLineSize());
        copy.setShape(type);
        return copy;
    }

    @Override
    public RoundRectView fromBean(BaseItemModule itemModule) {
        if (itemModule == null) return null;
        super.fromBean(itemModule);
//        RelativeLayout.LayoutParams layoutParams = new LayoutParams((int) itemModule.getWidth(), (int) itemModule.getHeight());
//        layoutParams.topMargin = (int) (itemModule.getY());
//        layoutParams.leftMargin = (int) (itemModule.getX());
//        setLayoutParams(layoutParams);
        setLineType(itemModule.getLineType());
        setLineSize(TranslateUtils.getPxByServicePx(TranslateUtils.getServicePxByServiceMm(itemModule.getLineWidth())));
        setShape(itemModule.getTagType() - 9);
        return this;
    }

    @Override
    public BaseItemModule toBean() {
        BaseItemModule itemModule = super.toBean();
        switch (type) {
            case 1:
                itemModule.setTagType(10);
                break;
            case 2:
                itemModule.setTagType(11);
                break;
            case 3:
                itemModule.setTagType(12);
                break;
            case 4:
                itemModule.setTagType(13);
                break;
        }
        itemModule.setShapeType(type - 1);
        itemModule.setLineWidth(TranslateUtils.getServiceMmByServicePx(TranslateUtils.getPxByLocalPx(getLineSize())));
//        itemModule.setX(getLeft());
//        itemModule.setY((getTop()));
        itemModule.setLineType(getLinType());
//        itemModule.setHeight(getHeight());
//        itemModule.setWidth(getWidth());
        return itemModule;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setDrawBottomBitmap(true);
        rectF.left = strokeWidth / 2;
        rectF.top = strokeWidth / 2;
        rectF.right = endWidth - strokeWidth / 2;
        rectF.bottom = endHeight - strokeWidth / 2;
        setScan(false);
        switch (type) {
            case 1:
                setScan(true);
                setDrawBottomBitmap(false);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, (Math.min((getWidth() - horizontalBitmapHeight) / 2, (getHeight() - verticalBitmapHeight) / 2)), linePaint);
                break;
            case 2:
                canvas.drawOval(rectF, linePaint);
                break;
            case 3:
                canvas.drawRect(rectF, linePaint);
                break;
            case 4:
                canvas.drawRoundRect(rectF, 50, 50, linePaint);
                break;
        }

        super.onDraw(canvas);
    }

    @Override
    public void setLineType(int type) {
        item.setLineType(type);
    }


    /**
     * @param type 1==圆形 2==椭圆 3==矩形 4==圆角矩形
     */
    @Override
    public void setShape(int type) {
        this.type = type;
        if (type == 1) {
            getLayoutParams().height = Math.min(getLayoutParams().height, getLayoutParams().width);
            getLayoutParams().width = Math.min(getLayoutParams().height, getLayoutParams().width);
            requestLayout();
        }
        invalidate();
    }

    @Override
    public void setLineSize(float size) {
        item.setLineSize(size);
    }

    @Override
    public int getLinType() {
        return item.getLinType();
    }

    @Override
    public float getLineSize() {
        return item.getLineSize();
    }
}
