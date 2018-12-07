package com.example.print.app.new_print.table;


import com.example.print.app.new_print.text.ITextItem;

/**
 * 表格接口
 */
interface ITableItemView extends ITextItem {

    /**
     * 设置行数
     */
    void setLineCount(int lineCount);

    /**
     * 得到行数
     */
    int getLineCount();

    /**
     * 设置列数
     */
    void setColumnCount(int lineCount);

    /**
     * 得到列数
     */
    int getColumnCount();

    /**
     * 设置外框线宽
     */
    void setOutStrokeWidth(float outStrokeWidth);

    /**
     * 得到外框线宽
     */
    float getOutStrokeWidth();

    /**
     * 设置内框线宽
     */
    void setInStrokeWidth(float inStrokeWidth);

    /**
     * 得到外框线宽
     */
    float getInStrokeWidth();

    /**
     * 设置单行的高度
     */
    void setLineHeight(int line, float height);

    /**
     * 得到单行高度
     */
    float getLineHeight(int line);

    /**
     * 设置单列的宽度
     */
    void setColumnWidth(int column, float width);

    /**
     * 获取单列的宽度
     */
    float getColumnWidth(int column);

    /**
     * 设置多选状态
     */
    void setMoreSelected(boolean b);

    /**
     * 获取多选状态
     */
    boolean setMoreSelected();
}
