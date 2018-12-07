package com.example.print.app.new_print.flow;


import java.nio.file.attribute.PosixFileAttributes;

public interface IFlowItem {
    /**
     * 获取文字
     */
    void setText(int position);

    /**
     * 设置文字
     */
    void setText();


    /**
     * 设置前缀
     */
    void setTextBefore(String textBefore);

    /**
     * 获取前缀
     */
    String getTextBefore();

    /**
     * 设置后缀
     */
    void setTextAfter(String textAfter);

    /**
     * 获取后缀
     */
    String getTextAfter();

    /**
     * 设置起始数字
     */
    void setTextStartNum(String startNum);

    /**
     * 获取起始数字
     */
    String getTextStartNum();

    void  setTextStep(int  step);
    int  getTextStep();



}
