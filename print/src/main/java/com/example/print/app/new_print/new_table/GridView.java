package com.example.print.app.new_print.new_table;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RequiresApi;
import com.example.print.app.R;
import com.example.print.app.new_print.module.CellPoint;
import com.project.skn.x.util.ToastInstance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GridView extends ViewGroup {
    private ArrayList<Integer> lines = new ArrayList<>();//所有的行
    private ArrayList<Integer> columns = new ArrayList<>();//所有的列
    private final int defaultLineHeight = 100;
    private final int defaultColumnsWidth = 200;
    private int lineWidth = 5;
    private int lineCount = 2;
    private int columnCount = 2;
    private boolean isSingle = true;

    private ArrayList<SelectTextView> tableModules = new ArrayList<>();
    private SelectTextView selectTextView;
    private ChangedListener changedListener;
    private SelectItemListener selectItemListener;


    private ArrayList<SelectTextView> merge = new ArrayList<>();

    public GridView(Context context, int lineCount, int columnCount) {
        super(context);
        this.lineCount = lineCount;
        this.columnCount = columnCount;
        init();
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    /**
     * 初始化数据
     */
    private void init() {
        setBackgroundResource(R.color.black);
        for (int i = 0; i < lineCount * columnCount; i++) {
            createTextView(i, defaultColumnsWidth, defaultLineHeight);
        }
        for (int i = 0; i < lineCount; i++) {
            lines.add(defaultLineHeight);
        }
        for (int i = 0; i < columnCount; i++) {
            columns.add(defaultColumnsWidth);
        }
    }

    /**
     * 拆分
     */
    public void split() {
        Iterator<SelectTextView> iterator = merge.iterator();
        while (iterator.hasNext()) {
            SelectTextView tableModule = iterator.next();
            if (tableModule.isSelected()) {
                for (int i = tableModule.getLine(); i < tableModule.getEndLine() + 1; i++) {
                    for (int j = tableModule.getColumn(); j < tableModule.getEndColumn() + 1; j++) {
                        SelectTextView t = searchTableModule(i, j);
                        if (t != null) t.setVisibility(View.VISIBLE);
                    }
                }
                removeView(tableModule);
                iterator.remove();
            }
        }
        clearSelect();
    }


    /**
     * 合并
     */
    public void merge() {
        CellPoint minCellPoint = new CellPoint();
        CellPoint maxCellPoint = new CellPoint();
        minCellPoint.cellColumn = columnCount;
        minCellPoint.cellRow = lineCount;
        maxCellPoint.cellColumn = 1;
        maxCellPoint.cellRow = 1;
        //第一步 遍历所有被选中的子单元格 计算范围
        for (int i = 0; i < getTableModules().size(); i++) {
            if (getTableModules().get(i).isSelected()) {
                if (getTableModules().get(i).getColumn() > maxCellPoint.cellColumn)
                    maxCellPoint.cellColumn = getTableModules().get(i).getColumn();
                if (getTableModules().get(i).getColumn() < minCellPoint.cellColumn)
                    minCellPoint.cellColumn = getTableModules().get(i).getColumn();
                if (getTableModules().get(i).getLine() > maxCellPoint.cellRow)
                    maxCellPoint.cellRow = getTableModules().get(i).getLine();
                if (getTableModules().get(i).getLine() < minCellPoint.cellRow)
                    minCellPoint.cellRow = getTableModules().get(i).getLine();
            }
        }
        //第二步 遍历所有被选中的合并单元格 计算范围
        for (int i = 0; i < merge.size(); i++) {
            if (merge.get(i).isSelected()) {
                if (merge.get(i).getEndColumn() > maxCellPoint.cellColumn)
                    maxCellPoint.cellColumn = merge.get(i).getEndColumn();
                if (merge.get(i).getColumn() < minCellPoint.cellColumn)
                    minCellPoint.cellColumn = merge.get(i).getColumn();
                if (merge.get(i).getEndLine() > maxCellPoint.cellRow) maxCellPoint.cellRow = merge.get(i).getEndLine();
                if (merge.get(i).getLine() < minCellPoint.cellRow) minCellPoint.cellRow = merge.get(i).getLine();
            }
        }
        //第三步 递归遍历范围判断范围内是否有 被合并的单元格 并再次计算范围 并移除范围内的合并单元格
        checkRange(minCellPoint, maxCellPoint);

        if (maxCellPoint.cellColumn - minCellPoint.cellColumn <= 0 && maxCellPoint.cellRow - minCellPoint.cellRow <= 0) {
            return;
        }
        //第四部 隐藏被合并的单元格 防止被点击
        hideCell(minCellPoint, maxCellPoint);

        createMergeTextView(minCellPoint, maxCellPoint);
        clearSelect();
    }

    /**
     * 隐藏被合并的单元格 防止被点击
     *
     * @param minCellPoint
     * @param maxCellPoint
     */
    public void hideCell(CellPoint minCellPoint, CellPoint maxCellPoint) {
        for (int i = minCellPoint.cellRow; i < maxCellPoint.cellRow + 1; i++) {
            for (int j = minCellPoint.cellColumn; j < maxCellPoint.cellColumn + 1; j++) {
                SelectTextView tableModule = searchTableModule(i, j);
                if (tableModule != null) tableModule.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 递归遍历范围判断范围内是否有 被合并的单元格 并再次计算范围
     */
    private void checkRange(CellPoint minCellPoint, CellPoint maxCellPoint) {
        boolean isContinue = false;
        for (int i = minCellPoint.cellRow; i < maxCellPoint.cellRow + 1; i++) {
            for (int j = minCellPoint.cellColumn; j < maxCellPoint.cellColumn + 1; j++) {
                SelectTextView tableModule = searchMergeTableModule(i, j);
                if (tableModule != null) {
                    if (tableModule.getEndColumn() > maxCellPoint.cellColumn)
                        maxCellPoint.cellColumn = tableModule.getEndColumn();
                    if (tableModule.getColumn() < minCellPoint.cellColumn)
                        minCellPoint.cellColumn = tableModule.getColumn();
                    if (tableModule.getEndLine() > maxCellPoint.cellRow)
                        maxCellPoint.cellRow = tableModule.getEndLine();
                    if (tableModule.getLine() < minCellPoint.cellRow) minCellPoint.cellRow = tableModule.getLine();
                    isContinue = true;
                    removeView(tableModule);
                    merge.remove(tableModule);
                }
            }
        }
        if (isContinue) {
            checkRange(minCellPoint, maxCellPoint);
        }
    }


    /**
     * 根据行列 寻找单元格
     *
     * @param line
     * @param column
     * @return
     */
    public SelectTextView searchTableModule(int line, int column) {
        for (int i = 0; i < tableModules.size(); i++) {
            if (line == tableModules.get(i).getLine() && column == tableModules.get(i).getColumn()) {
                return tableModules.get(i);
            }
        }
        return null;
    }

    /**
     * 根据行列 寻找合并单元格
     *
     * @param line
     * @param column
     * @return
     */
    public SelectTextView searchMergeTableModule(int line, int column) {
        for (int i = 0; i < merge.size(); i++) {
            if (line >= merge.get(i).getLine() && column >= merge.get(i).getColumn() && line <= merge.get(i).getEndLine() && column <= merge.get(i).getEndColumn()) {
                return merge.get(i);
            }
        }
        return null;
    }

    /**
     * 创建合并单元格
     */
    public SelectTextView createMergeTextView(CellPoint minCellPoint, CellPoint maxCellPoint) {
        SelectTextView selectTextView = new SelectTextView(getContext());
        selectTextView.setLine(minCellPoint.cellRow);
        selectTextView.setColumn(minCellPoint.cellColumn);
        selectTextView.setEndColumn(maxCellPoint.cellColumn);
        selectTextView.setEndLine(maxCellPoint.cellRow);
        selectTextView.setLayoutParams(sumMerge(selectTextView));
        SelectTextView child = searchTableModule(selectTextView.getLine(), selectTextView.getColumn());
        if (child != null) {
            selectTextView.setExcelData(child.getExcelData());
            selectTextView.setTextBold(child.getTextBold());
            selectTextView.setTextSize(child.getTextSize());
            selectTextView.setTextStyle(child.getTextStyle());
            selectTextView.setTextLetterSpacing(child.getTextLetterSpacing());
            selectTextView.setTextLineSpacing(child.getTextLineSpacing());
            selectTextView.setTextUnderline(child.getTextUnderline());
            selectTextView.setTextSkewX(child.getTextSkewX());
            selectTextView.setTexts(child.getText().toString());
            selectTextView.setChild(child);
        }
        merge.add(selectTextView);
        addView(selectTextView);
        return selectTextView;
    }

    /**
     * 计算合并项的宽高
     */
    private LayoutParams sumMerge(SelectTextView selectTextView) {
        int width = 0;
        int height = 0;
        for (int i = selectTextView.getColumn() - 1; i < selectTextView.getEndColumn(); i++) {
            width = width + columns.get(i);
        }
        width = width + (selectTextView.getEndColumn() - selectTextView.getColumn()) * lineWidth;

        for (int i = selectTextView.getLine() - 1; i < selectTextView.getEndLine(); i++) {
            height = height + lines.get(i);
        }
        height = height + (selectTextView.getEndLine() - selectTextView.getLine()) * lineWidth;
        return new LayoutParams(width, height);
    }

    /**
     * 移除行
     */
    public void removeLine() {
        if (lineCount <= 1) return;
        for (int i = 0; i < columns.size(); i++) {
            SelectTextView selectTextView = searchMergeTableModule(lineCount, i + 1);
            if (selectTextView != null) {
                ToastInstance.Companion.getINSTANCE().showToast("请先拆分单元格");
                return;
            }
        }
        lineCount = lineCount - 1;
        Iterator<SelectTextView> iterator = tableModules.iterator();
        while (iterator.hasNext()) {
            SelectTextView tableModule = iterator.next();
            if (tableModule.getLine() == lines.size()) {
                removeView(tableModule);
                iterator.remove();
            }
        }
        lines.remove(lines.size() - 1);

    }

    /**
     * 清空选中状态
     */
    public void clearSelect() {
        for (SelectTextView selectTextView : tableModules) {
            if (selectTextView.isSelected()) {
                selectTextView.setSelected(false);
            }
        }
        for (SelectTextView selectTextView : merge) {
            if (selectTextView.isSelected()) {
                selectTextView.setSelected(false);
            }
        }
    }

    /**
     * 移除列
     */
    public void removeColumn() {
        if (columnCount <= 1) return;
        for (int i = 0; i < lines.size(); i++) {
            SelectTextView tableModule = searchMergeTableModule(i + 1, columnCount);
            if (tableModule != null) {
                ToastInstance.Companion.getINSTANCE().showToast("请先拆分单元格");
                return;
            }
        }
        columnCount = columnCount - 1;
        Iterator<SelectTextView> iterator = tableModules.iterator();
        while (iterator.hasNext()) {
            SelectTextView tableModule = iterator.next();
            if (tableModule.getColumn() == columns.size()) {
                removeView(tableModule);
                iterator.remove();
            }
        }
        columns.remove(columns.size() - 1);
    }

    /**
     * 添加行
     */
    public void addLine() {
        lineCount = lineCount + 1;
        for (int i = 0; i < columns.size(); i++) {
            createTextView(tableModules.size(), columns.get(i), lines.get(lines.size() - 1));
        }
        lines.add(lines.get(lines.size() - 1));
    }

    /**
     * 添加列
     */
    public void addColumn() {
        columnCount = columnCount + 1;
        for (int i = 0; i < lines.size(); i++) {
            createTextView(columnCount - 1 + columnCount * i, columns.get(columns.size() - 1), lines.get(i));
        }
        columns.add(columns.get(columns.size() - 1));
    }

    /**
     * 创建图普通单元格
     *
     * @param position
     * @param width
     * @param height
     * @return
     */
    public SelectTextView createTextView(int position, int width, int height) {
        LayoutParams layoutParams = new LayoutParams(width, height);
        SelectTextView textView = new SelectTextView(getContext());
        textView.setLayoutParams(layoutParams);
        textView.setLine(position / columnCount + 1);
        textView.setColumn(position % columnCount + 1);
        tableModules.add(position, textView);
        addView(textView);
        return textView;
    }

    /**
     * 增加高度
     *
     * @param addHeight
     * @param position
     */
    public void addHeight(int addHeight, int position) {
        if (position > lineCount) return;
        if (addHeight + lines.get(position - 1) < 1) return;
        lines.set(position - 1, lines.get(position - 1) + addHeight);
//        for (int i = 0; i < tableModules.size(); i++) {
//            if (tableModules.get(i).getLine() == position) {
//                LayoutParams layoutParams = tableModules.get(i).getTextView().getLayoutParams();
//                layoutParams.height = tableModules.get(i).getTextView().getMeasuredHeight() + addHeight;
//                tableModules.get(i).getTextView().setLayoutParams(layoutParams);
//            }
//        }
        flashLayout();
    }

    /**
     * 真假宽度
     *
     * @param addWidth
     * @param position
     */
    public void addWidth(int addWidth, int position) {
        if (position > columnCount) return;
        if (addWidth + columns.get(position - 1) < 1) return;
        columns.set(position - 1, columns.get(position - 1) + addWidth);
//        for (int i = 0; i < tableModules.size(); i++) {
//            if (tableModules.get(i).getColumn() == position) {
//                LayoutParams layoutParams = tableModules.get(i).getTextView().getLayoutParams();
//                layoutParams.width = tableModules.get(i).getTextView().getMeasuredWidth() + addWidth;
//                tableModules.get(i).getTextView().setLayoutParams(layoutParams);
//            }
//        }
        flashLayout();
    }


    /**
     * 根据列宽列高重新计算子项宽高
     */
    public void flashLayout() {
        for (int i = 0; i < tableModules.size(); i++) {
            for (int j = 0; j < lines.size(); j++) {
                if (tableModules.get(i).getLine() - 1 == j) {
                    LayoutParams layoutParams = tableModules.get(i).getLayoutParams();
                    layoutParams.height = lines.get(j);
                    tableModules.get(i).setLayoutParams(layoutParams);
                }
            }
            for (int j = 0; j < columns.size(); j++) {
                if (tableModules.get(i).getColumn() - 1 == j) {
                    LayoutParams layoutParams = tableModules.get(i).getLayoutParams();
                    layoutParams.width = columns.get(j);
                    tableModules.get(i).setLayoutParams(layoutParams);
                }
            }
        }
        for (int i = 0; i < merge.size(); i++) {
            for (int j = 0; j < lines.size(); j++) {
                if (merge.get(i).getLine() - 1 == j) {
                    merge.get(i).setLayoutParams(sumMerge(merge.get(i)));
                }
            }
            for (int j = 0; j < columns.size(); j++) {
                if (merge.get(i).getColumn() - 1 == j) {
                    merge.get(i).setLayoutParams(sumMerge(merge.get(i)));
                }
            }
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (changedListener != null) changedListener.changedListener(lines, columns);
    }


    public void setSelectItemListener(SelectItemListener selectItemListener) {
        this.selectItemListener = selectItemListener;

    }

    public SelectItemListener getSelectItemListener() {
        return selectItemListener;
    }

    public void setChangedListener(ChangedListener listener) {
        changedListener = listener;
    }

    public SelectTextView getSelectTextView() {
        return selectTextView;
    }

    public void setSelectTextView(SelectTextView selectTextView) {
        this.selectTextView = selectTextView;
    }

    /**
     * 改变宽高的监听器
     */
    public interface ChangedListener {
        void changedListener(List<Integer> lines, List<Integer> columns);
    }

    /**
     * 选择ITEM监听器
     */
    public interface SelectItemListener {
        void selectListener(SelectTextView selectTextView);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int with = lineWidth;
        int height = lineWidth;
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int heigthMode = MeasureSpec.getMode(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //循环所有 行数为1 列数为1的 子VIew 算出父布局宽高
        for (int i = 0; i < tableModules.size(); i++) {
            if (tableModules.get(i).getLine() == 1) {
                with += tableModules.get(i).getMeasuredWidth() + lineWidth;
            }
            if (tableModules.get(i).getColumn() == 1) {
                height += tableModules.get(i).getMeasuredHeight() + lineWidth;
            }
        }
        setMeasuredDimension(with, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int t, int right, int b) {
        int w = 0;
        int h = 0;
        for (int i = 0; i < tableModules.size(); i++) {
            tableModules.get(i).layout(w + lineWidth, h + lineWidth, w + tableModules.get(i).getMeasuredWidth() + lineWidth
                    , h + tableModules.get(i).getMeasuredHeight() + lineWidth);
            if (tableModules.get(i).getColumn() == columnCount) {
                w = 0;
                h = h + lineWidth + tableModules.get(i).getMeasuredHeight();
            } else {
                w = w + lineWidth + tableModules.get(i).getMeasuredWidth();
            }
        }
        for (int i = 0; i < merge.size(); i++) {
            int w1 = lineWidth;
            int h1 = lineWidth;
            for (int j = 0; j < merge.get(i).getColumn() - 1; j++) {
                w1 += columns.get(j) + lineWidth;
            }
            for (int j = 0; j < merge.get(i).getLine() - 1; j++) {
                h1 += lines.get(j) + lineWidth;
            }
            merge.get(i).layout(w1, h1, w1 + merge.get(i).getMeasuredWidth(), h1 + merge.get(i).getMeasuredHeight());
        }
    }

    public void setLines(ArrayList<Integer> lines) {
        this.lines = lines;
        lineCount = lines.size();
    }

    public void setColumns(ArrayList<Integer> columns) {
        this.columns = columns;
        columnCount = columns.size();
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public int getLineCount() {
        return lineCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public ArrayList<Integer> getLines() {
        return lines;
    }

    public ArrayList<Integer> getColumns() {
        return columns;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        flashLayout();
    }

    public ArrayList<SelectTextView> getTableModules() {
        return tableModules;
    }

    public ArrayList<SelectTextView> getMerge() {
        return merge;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }
}
