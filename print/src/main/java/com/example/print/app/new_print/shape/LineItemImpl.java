package com.example.print.app.new_print.shape;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.View;

public class LineItemImpl implements LineItem {
    private int lineTpe;
    private Paint p;
    private View v;


    public LineItemImpl(Paint p, View v) {
        this.p = p;
        this.v = v;
    }

    // 设置形状线条类型 0 实线 1 虚线
    @Override
    public void setLineType(int lineType) {
        this.lineTpe=lineType;
        if (lineType == 1) {
            p.setPathEffect(new DashPathEffect(new float[]{20, 15}, 0)); // 虚线 间距10
        } else {
            p.setPathEffect(new DashPathEffect(new float[]{10, 0}, 0));
        }
        v.invalidate();
    }


    @Override
    public void setShape(int type) {



    }


    @Override
    public void setLineSize(float size) {
        p.setStrokeWidth(size);
        v.invalidate();
    }

    @Override
    public int getLinType() {
        return lineTpe;
    }

    @Override
    public float getLineSize() {
        return p.getStrokeWidth();
    }
}
