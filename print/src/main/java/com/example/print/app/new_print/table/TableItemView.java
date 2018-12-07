//package com.example.print.app.new_print.table;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.os.PostBuild;
//import android.support.annotation.Nullable;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.MotionEvent;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.print.app.new_print.base.BaseItemView;
//import com.example.print.app.new_print.text.ITextItem;
//import com.project.skn.x.dialog.DialogFactory;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * 表格
// */
//public class TableItemView extends BaseItemView implements ITableItemView, ITextItem {
//    private int lineCount = 3;
//    private int columnCount = 2;
//    private float outStrokeWidth;
//    private float inStrokeWidth;
//    private float itemHeight;
//    private float itemWidth;
//    private Paint outPaint;
//    private Paint inPaint;
//    private Rect outRect;
//    private List<TableModule> itemList = new ArrayList<>();
//    private List<Float> xPointList = new ArrayList<>();
//    private List<Float> yPointList = new ArrayList<>();
//    private Map<Integer, Float> lineHeightMap = new HashMap<>();
//    private Map<Integer, Float> columnWidthMap = new HashMap<>();
//    private boolean setMoreSelected;
//    private TableModule currentModule;
//    boolean isNeedDrawItemView;
//    private String textFont = "默认";
//
//    public static TableItemView create(Context context, int width, int height) {
//        TableItemView tableItemView = new TableItemView(context);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
//        tableItemView.setLayoutParams(layoutParams);
//        return tableItemView;
//    }
//
//    public TableItemView(Context context) {
//        super(context);
//    }
//
//    public TableItemView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//
//    @Override
//    public void newBuild() {
//        outStrokeWidth = 5;
//        inStrokeWidth = 5;
//        //外边框画笔
//        outPaint = new Paint();
//        outPaint.setAntiAlias(true);
//        outPaint.setStrokeWidth(outStrokeWidth);
//        outPaint.setColor(0xff000000);
//        outPaint.setStyle(Paint.Style.STROKE);
//        //内边框画笔
//        inPaint = new Paint();
//        inPaint.setAntiAlias(true);
//        inPaint.setStrokeWidth(inStrokeWidth);
//        inPaint.setColor(0xff000000);
//        inPaint.setStyle(Paint.Style.STROKE);
//    }
//    /**
//     * 属性框出现后的单击事件
//     */
//    @Override
//    public void downListener(MotionEvent event) {
//        //找到当前选中的并且刷新
//        for (TableModule tableModule : itemList) {
//            if (tableModule.getRect().contains((int) event.getX(), (int) event.getY())) {
//                tableModule.setSelected(true);
//                currentModule = tableModule;
//                break;
//            }
//        }
//        if (currentModule != null) {
//            for (TableModule tableModule : itemList) {
//                if (tableModule.getPosition() != currentModule.getPosition()) {
//                    if (!setMoreSelected) {
//                        tableModule.setSelected(false);
//                    }
//                }
//            }
//            isNeedDrawItemView = true;
//            invalidate();
//            drawItemView();
//        }
//
//    }
//
//
//
//    /**
//     * 拖拽监听
//     */
//    @Override
//    public void onSizeChanged() {
//        invalidate();
//
//    }
//
//    @Override
//    protected DialogFactory.Holder getDialog() {
//        return new TableItemDialog(getContext()).create(this);
//    }
//
//    public void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        initTable();
//        drawXY(canvas);
//    }
//
//    /**
//     * 初始化表格边框,线条,单元格
//     */
//    private void initTable() {
//        initBorder();
//        initXPointList();
//        initYPointList();
//        initItemList();
//    }
//
//    /**
//     * 初始化矩形
//     */
//    private void initBorder() {
//        outPaint.setStrokeWidth(outStrokeWidth);
//        inPaint.setStrokeWidth(inStrokeWidth);
//        //实际用来显示单元格的大小
//        float currentHeight = endHeight - outStrokeWidth + inStrokeWidth;
//        float currentWidth = endWidth - outStrokeWidth + inStrokeWidth;
//        //计算出程序第一次的单元格大小，以后采用累加方式
//        itemWidth = (currentWidth) / columnCount;
//        itemHeight = (currentHeight) / lineCount;
//        outRect = new Rect(0, 0, (endWidth), (endHeight));
//    }
//
//    /**
//     * 初始化 获取所有的X坐标点
//     */
//    public void initXPointList() {
//        xPointList.clear();
//        float lastX;
//        float itemSize;
//        float v;
//        if (outStrokeWidth == inStrokeWidth) {
//            v = 0;
//        } else {
//            if (outStrokeWidth / 2 > inStrokeWidth) {
//                v = outStrokeWidth / 2 - inStrokeWidth + inStrokeWidth / 2;
//            } else if (outStrokeWidth / 2 < inStrokeWidth) {
//                v = inStrokeWidth / 2 - inStrokeWidth + outStrokeWidth / 2;
//            } else {
//                v = inStrokeWidth / 2;
//            }
//        }
//        for (int i = 0; i <= columnCount; i++) {
//            lastX = i == 0 ? v : xPointList.get(i - 1);
//            itemSize = i == 0 ? 0 : columnWidthMap.get(i) == null ? itemWidth : columnWidthMap.get(i);
//            float x = lastX + itemSize;
//            xPointList.add(i, x);
//        }
//
//    }
//
//
//    /**
//     * 初始化 获取所有的Y坐标点
//     */
//    public void initYPointList() {
//        yPointList.clear();
//        inPaint.setStrokeWidth(inStrokeWidth);
//        float lastY;
//        float itemSize;
//        float v;
//        if (outStrokeWidth == inStrokeWidth) {
//            v = 0;
//        } else {
//            if (outStrokeWidth / 2 > inStrokeWidth) {
//                v = outStrokeWidth / 2 - inStrokeWidth + inStrokeWidth / 2;
//            } else if (outStrokeWidth / 2 < inStrokeWidth) {
//                v = inStrokeWidth / 2 - inStrokeWidth + outStrokeWidth / 2;
//            } else {
//                v = inStrokeWidth / 2;
//            }
//        }
//        for (int i = 0; i <= lineCount; i++) {
//            lastY = i == 0 ? v : yPointList.get(i - 1);
//            itemSize = i == 0 ? 0 : columnWidthMap.get(i) == null ? itemHeight : columnWidthMap.get(i);
//            float y = lastY + itemSize;
//            yPointList.add(i, y);
//        }
//    }
//
//    /**
//     * 初始化 获取所有的item坐标点
//     */
//    @SuppressLint("NewApi")
//    public void initItemList() {
//        int position = 0;
//        TextView textView;
//        Rect rect;
//        for (int i = 0; i < xPointList.size(); i++) {
//            for (int j = 0; j < yPointList.size(); j++) {
//                if (i > 0 && j > 0) {
//                    rect = new Rect();
//                    rect.left = (int) (xPointList.get(i - 1) + inStrokeWidth / 2);
//                    rect.top = (int) (yPointList.get(j - 1) + inStrokeWidth / 2);
//                    rect.right = (int) (xPointList.get(i) - inStrokeWidth / 2);
//                    rect.bottom = (int) (yPointList.get(j) - inStrokeWidth / 2);
//                    RelativeLayout.LayoutParams layoutParams = new LayoutParams(rect.right - rect.left, rect.bottom - rect.top);
//                    layoutParams.topMargin = rect.top;
//                    layoutParams.leftMargin = rect.left;
//                    TableModule tm = null;
//                    try {
//                        tm = itemList.get(position);
//                    } catch (Exception ignored) {
//                    } finally {
//                        if (tm == null) {
//                            //此时设置属性不会导致循环重绘 设置基本参数
//                            textView = new TextView(getContext());
//                            textView.setText("");
//                            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);
//                            textView.setGravity(Gravity.CENTER);
//                            tm = new TableModule();
//                            tm.setRect(rect);
//                            tm.setLine(j);
//                            tm.setColumn(i);
//                            tm.setPosition(position);
//                            tm.setTextView(textView);
//                            tm.setLayoutParams(layoutParams);
//                            tm.setSelected(i == 1 && j == 1);
//                            itemList.add(tm);
//                            Log.w("1234", "初始化单元格" + position);
//                        } else {
//                            Log.w("1234", "刷新单元格" + position);
//                            tm.setRect(rect);
//                            tm.setLine(j);
//                            tm.setColumn(i);
//                            tm.setPosition(position);
//                            tm.setLayoutParams(layoutParams);
//                            tm.setLayoutParams(layoutParams);
//                        }
//                    }
//                    position++;
//                }
//
//            }
//        }
//    }
//
//    /**
//     * 画xy
//     */
//    public void drawXY(Canvas canvas) {
//        canvas.drawRect(outRect, outPaint);
//        //画列
//        for (int i = 0; i < xPointList.size(); i++) {
//            if (i == 0 || i == xPointList.size() - 1) continue;
//            canvas.drawLine(xPointList.get(i), outStrokeWidth / 2, xPointList.get(i), endHeight - outStrokeWidth / 2, inPaint);
//        }
//        //画行
//        for (int i = 0; i < yPointList.size(); i++) {
//            if (i == 0 || i == yPointList.size() - 1) continue;
//            canvas.drawLine(outStrokeWidth / 2, yPointList.get(i), endWidth - outStrokeWidth / 2, yPointList.get(i), inPaint);
//        }
//    }
//
//    /**
//     * 画textView
//     */
//    private void drawItemView() {
//        for (TableModule tableModule : itemList) {
//            //重绘的时候  计算view的位置 重新设置
//            tableModule.getTextView().setBackgroundColor(tableModule.isSelected() ? Color.TRANSPARENT : Color.TRANSPARENT);
//            tableModule.getTextView().setLayoutParams(tableModule.getLayoutParams());
//            if (tableModule.getTextView().getParent() == null) {
//                addView(tableModule.getTextView());
//            }
//        }
//    }
//
//    /**
//     * 获取选中的单元格
//     */
//    private List<TableModule> getSelectorItem() {
//        List<TableModule> selected = new ArrayList<>();
//        for (TableModule tableModule : itemList) {
//            if (tableModule.isSelected()) {
//                selected.add(tableModule);
//            }
//        }
//        if (selected.size() == 0) {
//            selected.add(itemList.get(0));
//        }
//        return selected;
//    }
//
//    @Override
//    public void setText(String text) {
//        List<TableModule> selectorItem = getSelectorItem();
//        for (TableModule tableModule : selectorItem) {
//            tableModule.getTextView().setText(text);
//        }
//    }
//
//    @Override
//    public String getText() {
//        return getSelectorItem().get(0).getTextView().getText().toString();
//    }
//
//    @Override
//    public void setTextStyle(String textFont) {
////        if (TextUtils.isEmpty(textFont)) {
////            textView.setTypeface(Typeface.DEFAULT);
////        } else {
////            File file = new File(getContext().getExternalCacheDir(), textFont);
////            if (file.exists()) {
////                textView.setTypeface(Typeface.createFromFile(file));
////                this.textFont = textFont;
////            } else {
////                textView.setTypeface(Typeface.DEFAULT);
////            }
////        }
//
//    }
//
//    @Override
//    public String getTextStyle() {
//        return textFont;
//    }
//
//    @Override
//    public void setTextSize(float size) {
//        invalidate();
//        List<TableModule> selectorItem = getSelectorItem();
//        for (TableModule tableModule : selectorItem) {
//            tableModule.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
//        }
//    }
//
//    @Override
//    public float getTextSize() {
//        return getSelectorItem().get(0).getTextView().getTextSize();
//    }
//
//    @Override
//    public void setTextGravity(int gravity) {
//        List<TableModule> selectorItem = getSelectorItem();
//        for (TableModule tableModule : selectorItem) {
//            tableModule.getTextView().setGravity(gravity);
//        }
//    }
//
//    @Override
//    public int getTextGravity() {
//        return getSelectorItem().get(0).getTextView().getGravity();
//    }
//
//    @Override
//    public void setTextLetterSpacing(float space) {
//        if (PostBuild.VERSION.SDK_INT >= PostBuild.VERSION_CODES.LOLLIPOP) {
//            List<TableModule> selectorItem = getSelectorItem();
//            for (TableModule tableModule : selectorItem) {
//                tableModule.getTextView().setLetterSpacing(space);
//            }
//        } else {
//            Toast.makeText(getContext(), "系统版本不支持此功能", Toast.LENGTH_SHORT).showToast();
//        }
//
//    }
//
//    @Override
//    public float getTextLetterSpacing() {
//        if (PostBuild.VERSION.SDK_INT >= PostBuild.VERSION_CODES.LOLLIPOP) {
//            return getSelectorItem().get(0).getTextView().getLetterSpacing();
//        } else {
//            return 0;
//        }
//
//    }
//
//    @Override
//    public void setTextLineSpacing(float space) {
//        List<TableModule> selectorItem = getSelectorItem();
//        for (TableModule tableModule : selectorItem) {
//            tableModule.getTextView().setLineSpacing(space, 1);
//        }
//    }
//
//    @Override
//    public float getTextLineSpacing() {
//        return getSelectorItem().get(0).getTextView().getLineSpacingExtra();
//    }
//
//    @Override
//    public void setTextBold(boolean flag) {
//        List<TableModule> selectorItem = getSelectorItem();
//        for (TableModule tableModule : selectorItem) {
//            tableModule.getTextView().getPaint().setFakeBoldText(flag);
//        }
//    }
//
//    @Override
//    public boolean getTextBold() {
//        return getSelectorItem().get(0).getTextView().getPaint().isFakeBoldText();
//    }
//
//    @Override
//    public void setTextUnderline(boolean flag) {
//        List<TableModule> selectorItem = getSelectorItem();
//        for (TableModule tableModule : selectorItem) {
//            tableModule.getTextView().getPaint().setUnderlineText(flag);
//        }
//    }
//
//    @Override
//    public boolean getTextUnderline() {
//        return getSelectorItem().get(0).getTextView().getPaint().isUnderlineText();
//    }
//
//    @Override
//    public void setTextSkewX(float f) {
//        List<TableModule> selectorItem = getSelectorItem();
//        for (TableModule tableModule : selectorItem) {
//            tableModule.getTextView().getPaint().setTextSkewX(f);
//        }
//    }
//
//    @Override
//    public float getTextSkewX() {
//        return getSelectorItem().get(0).getTextView().getPaint().getTextSkewX();
//    }
//
//
//    @Override
//    public void setLineCount(int lineCount) {
//        ViewGroup.LayoutParams layoutParams = getLayoutParams();
//        layoutParams.height += (lineCount - this.lineCount) * itemHeight;
//        this.lineCount = lineCount;
//        setLayoutParams(layoutParams);
//
//    }
//
//    @Override
//    public int getLineCount() {
//        return lineCount;
//    }
//
//    @Override
//    public void setColumnCount(int columnCount) {
//        ViewGroup.LayoutParams layoutParams = getLayoutParams();
//        layoutParams.width += (columnCount - this.columnCount) * itemWidth;
//        this.columnCount = columnCount;
//        setLayoutParams(layoutParams);
//    }
//
//    @Override
//    public int getColumnCount() {
//        return columnCount;
//    }
//
//    @Override
//    public void setOutStrokeWidth(float outStrokeWidth) {
//        if (outStrokeWidth > inStrokeWidth) {
//            Toast.makeText(getContext(), "外宽不许小于内宽", Toast.LENGTH_SHORT).showToast();
//            return;
//        }
//        this.outStrokeWidth = outStrokeWidth;
//        invalidate();
//    }
//
//    @Override
//    public float getOutStrokeWidth() {
//        return outStrokeWidth;
//    }
//
//    @Override
//    public void setInStrokeWidth(float inStrokeWidth) {
//        if (inStrokeWidth > outStrokeWidth) {
//            Toast.makeText(getContext(), "内边框宽度不许超过外宽", Toast.LENGTH_SHORT).showToast();
//            return;
//        }
//        this.inStrokeWidth = inStrokeWidth;
//        invalidate();
//    }
//
//    @Override
//    public float getInStrokeWidth() {
//        return inStrokeWidth;
//    }
//
//    @Override
//    public void setLineHeight(int line, float height) {
//        lineHeightMap.put(line, height);
//    }
//
//    @Override
//    public float getLineHeight(int line) {
//        return lineHeightMap.get(line) == null ? itemHeight : lineHeightMap.get(line);
//    }
//
//    @Override
//    public void setColumnWidth(int column, float width) {
//        columnWidthMap.put(column, width);
//    }
//
//    @Override
//    public float getColumnWidth(int column) {
//        return columnWidthMap.get(column) == null ? itemWidth : columnWidthMap.get(column);
//    }
//
//
//    @Override
//    public void setMoreSelected(boolean b) {
//        this.setMoreSelected = b;
//    }
//
//    @Override
//    public boolean setMoreSelected() {
//        return setMoreSelected;
//    }
//}
