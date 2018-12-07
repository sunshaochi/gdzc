package com.example.print.app.new_print.text;

import android.graphics.Typeface;

/**
 * 文本框的接口定义
 */
public interface ITextItem {
    /**
     * 设置文字
     */
    void setText(String text);

    /**
     * 设置文本
     */
    String getText();

    /**
     * 设置字体样式
     */
    void setTextStyle(String textFont);



    /**
     * 获取字体样式
     */
    String getTextStyle();

    /**
     * 设置字体大小
     */
    void setTextSize(float size);

    /**
     * 获得字体大小
     */
    float getTextSize();

    /**
     * 设置字体对齐
     */
    void setTextGravity(int gravity);

    /**
     * 获取字体对齐
     */
    int getTextGravity();

    /**
     * 设置字体间距
     */
    void setTextLetterSpacing(float space);

    /**
     * 获取字体间距
     */
    float getTextLetterSpacing();

    /**
     * 设置字体行距
     */
    void setTextLineSpacing(float space);

    /**
     * 获取字体行距
     */
    float getTextLineSpacing();

    /**
     * 设置字体粗体
     */
    void setTextBold(boolean flag);

    /**
     * 是否加粗
     */
    boolean getTextBold();

    /**
     * 设置字体下划线
     */
    void setTextUnderline(boolean flag);

    /**
     * 是否有下划线
     */
    boolean getTextUnderline();

    /**
     * 设置字体斜体
     */
    void setTextSkewX(float f);

    /**
     * 获取斜体角度
     */
    float getTextSkewX();

}
