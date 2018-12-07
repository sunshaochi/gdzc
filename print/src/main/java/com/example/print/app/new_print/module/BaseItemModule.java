package com.example.print.app.new_print.module;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 元素module
 */
public class BaseItemModule implements Serializable {
    //base
    protected int tagType;
    protected float x;
    protected float y;
    protected float height;
    protected float width;
    protected int rotate;
    // TODO: 2018/8/29 需要解耦封装
    //文本
    private int textAlignment;
    private int fontProperty;
    private float textSize;
    private String textFont = "";
    private String excelString = "";
    private String string = "";
    private boolean fakeBoldText;
    private boolean underlineText;
    private boolean textSkewX;
    private float lineSpacing;
    private float fontSpacing;
    //流水号
    private String serialPrefix = ""; //前缀
    private String serialSuffix = ""; //后缀
    private String serialBits = ""; //位数
    private String increasesNum = "";//递增

    public String getIncreasesNum() {
        return increasesNum;
    }

    public void setIncreasesNum(String increasesNum) {
        this.increasesNum = increasesNum;
    }

    private String serialStart = ""; //起始数值

    //时间
    private String dateFormat1 = ""; // 时间格式
    private String dateFormat2 = ""; // 日期格式
    private String data = "";
    private String time = "";
    //图片
    private String imageUrl = "";
    //一维码
    private int barcodeFormat;
    private int stringLocation;
    // 线条
    private int lineHSType; // 0 横线 1 竖线
    private int lineType; // 0 实线  1 虚线
    // 形状
    private int shapeType;  // 图形 0 1 2 3  （圆，椭圆，方形，圆角方形） 默认 0
    private float lineWidth;//线宽

    private ArrayList<Float> formColumnWidths;

    private ArrayList<Float> formRowHeights;

    private ArrayList<ArrayList<BaseItemModule>> formItems;

    private ArrayList<MergeCellInfos> mergeCellInfos;

    public int getTagType() {
        return tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    public int getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(int textAlignment) {
        this.textAlignment = textAlignment;
    }

    public int getFontProperty() {
        return fontProperty;
    }

    public void setFontProperty(int fontProperty) {
        this.fontProperty = fontProperty;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public String getTextFont() {
        return textFont;
    }

    public void setTextFont(String textFont) {
        this.textFont = textFont;
    }

    public String getExcelString() {
        return excelString;
    }

    public void setExcelString(String excelString) {
        this.excelString = excelString;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public boolean isFakeBoldText() {
        return fakeBoldText;
    }

    public void setFakeBoldText(boolean fakeBoldText) {
        this.fakeBoldText = fakeBoldText;
    }

    public boolean isUnderlineText() {
        return underlineText;
    }

    public void setUnderlineText(boolean underlineText) {
        this.underlineText = underlineText;
    }

    public boolean isTextSkewX() {
        return textSkewX;
    }

    public void setTextSkewX(boolean textSkewX) {
        this.textSkewX = textSkewX;
    }

    public float getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(float lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public float getFontSpacing() {
        return fontSpacing;
    }

    public void setFontSpacing(float fontSpacing) {
        this.fontSpacing = fontSpacing;
    }

    public String getSerialPrefix() {
        return serialPrefix;
    }

    public void setSerialPrefix(String serialPrefix) {
        this.serialPrefix = serialPrefix;
    }

    public String getSerialSuffix() {
        return serialSuffix;
    }

    public void setSerialSuffix(String serialSuffix) {
        this.serialSuffix = serialSuffix;
    }

    public String getSerialBits() {
        return serialBits;
    }

    public void setSerialBits(String serialBits) {
        this.serialBits = serialBits;
    }

    public String getSerialStart() {
        return serialStart;
    }

    public void setSerialStart(String serialStart) {
        this.serialStart = serialStart;
    }

    public String getDateFormat1() {
        return dateFormat1;
    }

    public void setDateFormat1(String dateFormat1) {
        this.dateFormat1 = dateFormat1;
    }

    public String getDateFormat2() {
        return dateFormat2;
    }

    public void setDateFormat2(String dateFormat2) {
        this.dateFormat2 = dateFormat2;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getBarcodeFormat() {
        return barcodeFormat;
    }

    public void setBarcodeFormat(int barcodeFormat) {
        this.barcodeFormat = barcodeFormat;
    }

    public int getStringLocation() {
        return stringLocation;
    }

    public void setStringLocation(int stringLocation) {
        this.stringLocation = stringLocation;
    }

    public int getLineHSType() {
        return lineHSType;
    }

    public void setLineHSType(int lineHSType) {
        this.lineHSType = lineHSType;
    }

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
    }

    public int getShapeType() {
        return shapeType;
    }

    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }


    public ArrayList<ArrayList<BaseItemModule>> getFormItems() {
        return formItems;
    }

    public void setFormItems(ArrayList<ArrayList<BaseItemModule>> formItems) {
        this.formItems = formItems;
    }

    public ArrayList<MergeCellInfos> getMergeCellInfos() {
        return mergeCellInfos;
    }

    public void setMergeCellInfos(ArrayList<MergeCellInfos> mergeCellInfos) {
        this.mergeCellInfos = mergeCellInfos;
    }

    public ArrayList<Float> getFormColumnWidths() {
        return formColumnWidths;
    }

    public void setFormColumnWidths(ArrayList<Float> formColumnWidths) {
        this.formColumnWidths = formColumnWidths;
    }

    public ArrayList<Float> getFormRowHeights() {
        return formRowHeights;
    }

    public void setFormRowHeights(ArrayList<Float> formRowHeights) {
        this.formRowHeights = formRowHeights;
    }
}
