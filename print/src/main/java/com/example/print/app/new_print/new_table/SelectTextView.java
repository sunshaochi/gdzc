package com.example.print.app.new_print.new_table;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.print.app.R;
import com.example.print.app.new_print.ExcelChangeManager;
import com.example.print.app.new_print.text.IExcelTextItem;
import com.example.print.app.util.TranslateUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class SelectTextView extends AppCompatTextView implements IExcelTextItem, ExcelChangeManager.IExcelDataChangeListener {
    private GridView gridView;
    private List<String> excelData = new ArrayList<>();
    protected String textFont = "默认";
    private int excelPosition;
    private int line;
    private int column;
    private int endLine;
    private int endColumn;
    private SelectTextView child;

    public SelectTextView(Context context) {
        super(context);
        init();
    }

    public SelectTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getParent() instanceof GridView) {
            gridView = (GridView) getParent();
        }
        ExcelChangeManager.getManager().registerChange(this);
    }

    private void init() {
        setTextColor(getResources().getColor(R.color.black));
        setBackgroundResource(R.drawable.selector_tabletext);
        setGravity(Gravity.CENTER);
        setClickable(true);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, TranslateUtils.getLocationTextSize(2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (gridView != null) {
                    if (gridView.isSingle()) {
                        if (!isSelected()) {
                            gridView.clearSelect();
                            setSelected(true);
                        }
                    } else {
                        setSelected(!isSelected());
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void setSelected(boolean selected) {
        if (!isSelected() && selected) {
            gridView.setSelectTextView(this);
            if (gridView.getSelectItemListener() != null) {
                gridView.getSelectItemListener().selectListener(this);
            }
        }
        super.setSelected(selected);
    }

    @Override
    public void onUpdate(int position) {
        excelPosition = position;
        if (excelData != null) {
            if (excelData.size() > 0) {

                if (position < excelData.size()) {
                    setText(excelData.get(position));
                }
            }
        }
    }

    @Override
    public void setExcelData(List<String> excelData) {
        this.excelData = excelData;
        if (child != null) child.setExcelData(excelData);
    }

    @Override
    public CharSequence getText() {
        return super.getText();
    }

    @Override
    public List<String> getExcelData() {
        return excelData == null ? new ArrayList<>() : excelData;
    }

    @Override
    public boolean hasExcelData() {
        return excelData != null && excelData.size() > 0;
    }


    public void setTextStyle(String textFont) {
        if (TextUtils.isEmpty(textFont)) {
            setTypeface(Typeface.DEFAULT);
            this.textFont = "默认";
        } else {
            File file = new File(getContext().getExternalCacheDir() + "/font_manager", textFont);
            if (file.exists()) {
                try {
                    setTypeface(Typeface.createFromFile(file));
                } catch (Exception e) {
                    setTypeface(Typeface.DEFAULT);
                    this.textFont = "默认";
                }
            } else {
                setTypeface(Typeface.DEFAULT);
                this.textFont = "默认";
            }
            this.textFont = textFont;
        }
        if (child != null) child.setTextStyle(textFont);
    }

    @Override
    public void setTextSize(float size) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        if (child != null) child.setTextSize(size);
    }

    public String getTextStyle() {
        return textFont;
    }


    public void setTextLetterSpacing(float space) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setLetterSpacing(space);
        } else {
            Toast.makeText(getContext(), "系统版本不支持此功能", Toast.LENGTH_SHORT).show();
        }
        if (child != null) child.setTextLetterSpacing(space);
    }

    public float getTextLetterSpacing() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getLetterSpacing();
        } else {
            return 0;
        }
    }

    public void setTextLineSpacing(float space) {
        setLineSpacing(space, 1);
        if (child != null) child.setLineSpacing(space, 1);
    }

    public float getTextLineSpacing() {
        return getLineSpacingExtra();
    }

    public void setTextBold(boolean flag) {
        getPaint().setFakeBoldText(flag);
        invalidate();
        if (child != null) child.setTextBold(flag);
    }

    public boolean getTextBold() {
        return getPaint().isFakeBoldText();
    }

    public void setTextUnderline(boolean flag) {
        getPaint().setUnderlineText(flag);
        invalidate();
        if (child != null) child.setTextUnderline(flag);
    }

    public boolean getTextUnderline() {
        return getPaint().isUnderlineText();
    }

    public void setTextSkewX(float f) {
        getPaint().setTextSkewX(f);
        invalidate();
        if (child != null) child.setTextSkewX(f);
    }

    public float getTextSkewX() {
        return getPaint().getTextSkewX();
    }

    public void setTexts(String text) {
        setText(text);
        if (hasExcelData()) {
            if (excelPosition < excelData.size())
                excelData.set(excelPosition, text);
        }
        if (child != null) child.setTexts(text);
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }

    public SelectTextView getChild() {
        return child;
    }

    public void setChild(SelectTextView child) {
        this.child = child;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ExcelChangeManager.getManager().unregisterChange(this);
    }


}
