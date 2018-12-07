//package com.example.print.app.new_print.table;
//
//import android.graphics.Typeface;
//import android.view.Gravity;
//import android.view.ViewGroup;
//import com.example.print.app.new_print.new_table.SelectTextView;
//
//import java.io.Serializable;
//
//public class TableModule implements Serializable {
//
//    private SelectTextView textView;
//    private int line;
//    private int column;
//    private int endLine;
//    private int endColumn;
//    private ViewGroup.LayoutParams layoutParams;
//
//    public ViewGroup.LayoutParams getLayoutParams() {
//        return layoutParams;
//    }
//
//    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
//        this.layoutParams = layoutParams;
//    }
//
//
//    private int background;
//    private float textSize = 24;
//    private int gravity = Gravity.CENTER;
//    private float letterSpace;
//    private float lineSpace;
//    private boolean isBold;
//    private boolean isUnderlineTex;
//    private float isTextSkewX;
//    private Typeface typeface = Typeface.DEFAULT;
//    private String text = "";
//
//    public float getTextSize() {
//        return textSize;
//    }
//
//    public void setTextSize(float textSize) {
//        this.textSize = textSize;
//    }
//
//    public int getGravity() {
//        return gravity;
//    }
//
//    public void setGravity(int gravity) {
//        this.gravity = gravity;
//    }
//
//    public float getFontSpacing() {
//        return letterSpace;
//    }
//
//    public void setFontSpacing(float letterSpace) {
//        this.letterSpace = letterSpace;
//    }
//
//    public float getLineSpacing() {
//        return lineSpace;
//    }
//
//    public void setLineSpacing(float lineSpace) {
//        this.lineSpace = lineSpace;
//    }
//
//    public boolean isBold() {
//        return isBold;
//    }
//
//    public void setBold(boolean bold) {
//        isBold = bold;
//    }
//
//    public boolean isUnderlineTex() {
//        return isUnderlineTex;
//    }
//
//    public void setUnderlineTex(boolean underlineTex) {
//        isUnderlineTex = underlineTex;
//    }
//
//    public float getIsTextSkewX() {
//        return isTextSkewX;
//    }
//
//    public void setIsTextSkewX(float isTextSkewX) {
//        this.isTextSkewX = isTextSkewX;
//    }
//
//    public Typeface getTypeface() {
//        return typeface;
//    }
//
//    public void setTypeface(Typeface typeface) {
//        this.typeface = typeface;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public int getBackground() {
//        return background;
//    }
//
//    public void setBackground(int background) {
//        this.background = background;
//    }
//
//    public TableModule(SelectTextView textView, int line, int column) {
//        this.textView = textView;
//        this.line = line;
//        this.column = column;
//    }
//
//    public TableModule() {
//    }
//
//    public SelectTextView getTextView() {
//        return textView;
//    }
//
//    public void setTextView(SelectTextView textView) {
//        this.textView = textView;
//    }
//
//    public int getLine() {
//        return line;
//    }
//
//    public void setLine(int line) {
//        this.line = line;
//    }
//
//    public int getColumn() {
//        return column;
//    }
//
//    public void setColumn(int column) {
//        this.column = column;
//    }
//
//    public int getEndLine() {
//        return endLine;
//    }
//
//    public void setEndLine(int endLine) {
//        this.endLine = endLine;
//    }
//
//    public int getEndColumn() {
//        return endColumn;
//    }
//
//    public void setEndColumn(int endColumn) {
//        this.endColumn = endColumn;
//    }
//}
