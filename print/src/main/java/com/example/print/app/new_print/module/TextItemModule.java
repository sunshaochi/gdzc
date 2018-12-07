package com.example.print.app.new_print.module;


public class TextItemModule extends BaseItemModule {
    private int textAlignment;
    private int fontProperty;
    private float textSize;
    private String textFont;
    private String excelString;
    private String string;
    private boolean fakeBoldText;
    private boolean underlineText;
    private boolean textSkewX;

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

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
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
}
