package com.example.print.app.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import com.example.print.app.new_print.base.BaseItemView;
import com.example.print.app.new_print.base.PrintView;

import java.util.ArrayList;

public class ViewCacheUtil {
    private static Bitmap.Config bitmap_quality = Bitmap.Config.ARGB_8888;
    private static boolean quick_cache = false;

    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    private static Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }


    /**
     * 拿到bitmap图片
     */
    public static Bitmap getMagicDrawingCache(View view) {
        if (view instanceof PrintView) {
            PrintView printView = (PrintView) view;
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.WHITE);
            Canvas canvas = new Canvas(bitmap);
            ArrayList<BaseItemView> itemViewList = printView.getItemViewList();
            for (int i = 0; i < itemViewList.size(); i++) {
                BaseItemView baseItemView = itemViewList.get(i);
                if (baseItemView instanceof BaseItemView) {
                    boolean selected = baseItemView.isSelected();
                    if (selected) {
                        baseItemView.setSelected(false);
                    }
                    canvas.save();
                    canvas.translate(baseItemView.getLeft(), baseItemView.getTop());
                    baseItemView.draw(canvas);
                    canvas.rotate(baseItemView.getRotateSum(), baseItemView.getLeft() + baseItemView.getWidth() / 2, baseItemView.getTop() + baseItemView.getHeight() / 2);
                    canvas.translate(baseItemView.getLeft(), baseItemView.getTop());
                    baseItemView.draw(canvas);
                    canvas.restore();
                    if (selected) {
                        baseItemView.setSelected(true);
                    }
                } else {
                    canvas.save();
                    canvas.translate(printView.getChildAt(i).getLeft(), printView.getChildAt(i).getTop());
                    printView.getChildAt(i).draw(canvas);
                    canvas.restore();
                }

            }
            return bitmap;
        } else {
            throw new NullPointerException("！");
        }

    }

    /**
     * 获取view的bitmap
     */
    public static Bitmap getBitmap(View view) {
        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();
        if (viewHeight == 0 || viewWidth == 0) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(viewWidth, viewHeight, bitmap_quality);
        bitmap.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


}
