package com.example.print.app.new_print.shape.line;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.print.app.new_print.base.BaseItemView;
import com.example.print.app.new_print.module.BaseItemModule;
import com.example.print.app.new_print.shape.LineItem;
import com.example.print.app.new_print.shape.LineItemImpl;
import com.example.print.app.util.TranslateUtils;

public class LineItemView extends BaseItemView implements LineItem {
    private Paint linePaint;
    private LineItem item;
    private int type = 0;
    private int paintType = 0;


//    @Override
//    protected DialogFactory.Holder getDialog() {
//        return new LineItemDialog(getContext()).create(this);
//    }

    public LineItemView(Context context) {
        super(context);
        setDrawDottedLine(false);
        linePaint = new Paint();
        item = new LineItemImpl(linePaint, this);
        linePaint.setColor(0xff000000);
        linePaint.setStrokeWidth(10);
        linePaint.setStyle(Paint.Style.FILL);
    }


    public static LineItemView create(Context context, int length) {
        LineItemView lineItemView = new LineItemView(context);
        lineItemView.setSelected(true);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(length, ViewGroup.LayoutParams.WRAP_CONTENT);
        lineItemView.setLayoutParams(layoutParams);
        return lineItemView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (type == 0) {
            int widthMeasure = getLayoutParams().width;
            int heightMeasure = (int) (horizontalBitmap.getHeight() * 1.5);
            setMeasuredDimension(widthMeasure, heightMeasure);
        } else if (type == 1) {
            int widthMeasure = (int) (horizontalBitmap.getWidth() * 1.5);
            int heightMeasure = getLayoutParams().height;
            setMeasuredDimension(widthMeasure, heightMeasure);
        }

    }

    @Override
    public void amplification() {
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        layoutParams.width += 5;
        setLayoutParams(layoutParams);
    }

    @Override
    public void narrow() {
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        layoutParams.width -= 5;
        setLayoutParams(layoutParams);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (type == 0) {
            setDrawBottomBitmap(false);
            setDrawRightBitmap(true);
            if (paintType == 0)
                canvas.drawLine(0, getHeight() / 2, getWidth() - horizontalBitmap.getWidth() / 2, getHeight() / 2, linePaint);
            else {
                for (int i = 0; i < (getWidth() - horizontalBitmapWidth / 2) / 30; i++) {
                    canvas.drawLine(i * 30, getHeight() / 2, i * 30 + 20, getHeight() / 2, linePaint);
                }
            }
        } else {
            setDrawBottomBitmap(true);
            setDrawRightBitmap(false);
            if (paintType == 0)
                canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight() - verticalBitmap.getHeight() / 2, linePaint);
            else {
                for (int i = 0; i < (getHeight() - verticalBitmapHeight / 2) / 30; i++) {
                    canvas.drawLine(getWidth() / 2, i * 30, getWidth() / 2, i * 30 + 20, linePaint);
                }
            }
        }
        super.onDraw(canvas);
    }

    @Override
    public BaseItemView copy() {
        LineItemView copy = (LineItemView) super.copy();
        copy.setLineType(item.getLinType());
        copy.type = type;
        copy.setLineSize(item.getLineSize());
        return copy;
    }

    @Override
    public LineItemView fromBean(BaseItemModule itemModule) {
        if (itemModule == null) return null;
        super.fromBean(itemModule);
        if (itemModule.getTagType() == 6) {
            this.type = 1;
        } else if (itemModule.getTagType() == 7) {
            this.type = 0;
        }
        setLineType(itemModule.getLineType());
        setLineSize(TranslateUtils.getPxByServicePx(TranslateUtils.getServicePxByServiceMm(itemModule.getLineWidth())));
//        RelativeLayout.LayoutParams layoutParams = new LayoutParams((int) itemModule.getWidth(), (int) itemModule.getHeight());
//        layoutParams.topMargin = (int) (itemModule.getY());
//        layoutParams.leftMargin = (int) (itemModule.getX());
//        this.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * = 6;//竖线  7;//横线
     *
     * @return
     */
    @Override
    public BaseItemModule toBean() {
        BaseItemModule itemModule = super.toBean();
        if (type == 0) {
            itemModule.setTagType(7);
        } else if (type == 1) {
            itemModule.setTagType(6);
        }
        itemModule.setLineWidth(TranslateUtils.getServiceMmByServicePx(TranslateUtils.getPxByLocalPx(getLineSize())));
//        itemModule.setX(getLeft());
//        itemModule.setY((getTop()));
        itemModule.setLineType(getLinType());
//        itemModule.setHeight(getHeight());
//        itemModule.setWidth(getWidth());
        return itemModule;
    }

    @Override
    public void setLineType(int type) {
//        item.setLineType(type);
        paintType = type;
        invalidate();
    }

    @Override
    public void setShape(int type) {
        if (this.type == type) return;
        this.type = type;
        ViewGroup.LayoutParams layoutParams;
        if (type == 0) {
            //横线
            layoutParams = getLayoutParams();
            layoutParams.width = getHeight();
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            layoutParams = getLayoutParams();
            layoutParams.height = getWidth();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        setLayoutParams(layoutParams);
    }

    @Override
    public void setLineSize(float size) {
        item.setLineSize(size);
    }

    @Override
    public int getLinType() {
        return paintType;
    }

    @Override
    public float getLineSize() {
        return item.getLineSize();
    }
}
