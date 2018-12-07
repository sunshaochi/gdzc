package com.example.print.app.new_print.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.print.app.new_print.ExcelChangeManager;
import com.example.print.app.new_print.base.BaseItemView;
import com.example.print.app.new_print.module.BaseItemModule;
import com.example.print.app.util.TranslateUtils;
import com.project.skn.x.util.SampleTextWatcher;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 文本
 */
public class TextItemView extends BaseItemView implements ITextItem, IExcelTextItem, ExcelChangeManager.IExcelDataChangeListener {


    protected TextView textView;
    protected String textFont = "默认";
    private List<String> excelData = new ArrayList<>();
    private float lineSpace = 0f;
    private int excelPosition;

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public static TextItemView create(Context context) {
        TextItemView textItemView = new TextItemView(context);
        Paint paint = new Paint();
        paint.setTextSize(TranslateUtils.getLocationTextSize(2));
        int width = (int) (paint.measureText("双击文本框编辑") + textItemView.horizontalBitmapWidth);
        LayoutParams layoutParams = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
        textItemView.setLayoutParams(layoutParams);
        return textItemView;
    }

    public TextItemView(Context context) {
        super(context);
        setSelected(true);
        setDrawBottomBitmap(false);
        textView = new TextView(getContext());
        setHint("双击文本框编辑");
        textView.setMinWidth((int) minWidth);
        textView.setTextColor(Color.BLACK);
        textView.setHintTextColor(Color.BLACK);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, TranslateUtils.getLocationTextSize(2));
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        addView(textView);
    }

    public void setHint(String hint) {
        textView.setText(hint);
        textView.addTextChangedListener(new SampleTextWatcher() {
            @Override
            public void afterTextChanged(@Nullable Editable s) {
                if (s != null && s.length() == 0) {
                    textView.setText(hint);
                }
            }
        });
    }


    @Override
    public void amplification() {
        Integer position = TranslateUtils.getPositionFromLocal(getTextSize());
        Float locationTextSize = TranslateUtils.getLocationTextSize(position + 1);
        setTextSize(locationTextSize);
    }

    @Override
    public void narrow() {
        Integer position = TranslateUtils.getPositionFromLocal(getTextSize());
        Float locationTextSize = TranslateUtils.getLocationTextSize(position - 1);
        setTextSize(locationTextSize);
    }


    @Override
    public void setText(String text) {
        textView.setText(text);
        if (hasExcelData()) {
            if (excelPosition < excelData.size())
                excelData.set(excelPosition, text);
        }
    }

    @Override
    public String getText() {
        return textView.getText().toString();
    }

    @Override
    public void setTextStyle(String textFont) {
        if (TextUtils.isEmpty(textFont)) {
            textView.setTypeface(Typeface.DEFAULT);
            this.textFont = "默认";
        } else {
            File file = new File(getContext().getExternalCacheDir() + "/font_manager", textFont);
            if (file.exists()) {
                try {
                    textView.setTypeface(Typeface.createFromFile(file));
                } catch (Exception e) {
                    textView.setTypeface(Typeface.DEFAULT);
                    this.textFont = "默认";
                }
            } else {
                textView.setTypeface(Typeface.DEFAULT);
                this.textFont = "默认";
            }
            this.textFont = textFont;
        }

    }

    @Override
    public String getTextStyle() {
        return textFont;
    }

    @Override
    public void setTextSize(float size) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    @Override
    public float getTextSize() {
        return textView.getTextSize();
    }

    @Override
    public void setTextGravity(int gravity) {
        textView.setGravity(gravity);
    }

    @Override
    public int getTextGravity() {
        return textView.getGravity();
    }

    @Override
    public void setTextLetterSpacing(float space) {
        if (space != 0) {
            space = space / 10;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textView.setLetterSpacing(space);
        } else {
            Toast.makeText(getContext(), "系统版本不支持此功能", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public float getTextLetterSpacing() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BigDecimal bd = new BigDecimal((double) textView.getLetterSpacing() * 10);
            bd = bd.setScale(2, 4);
            return bd.floatValue();
        } else {
            return 0;
        }
    }

    @Override
    public void setTextLineSpacing(float space) {
        lineSpace = space;
        if(space==2){
            textView.setLineSpacing(space * 8, 1.5F);
        }
        if(space==3){
            textView.setLineSpacing(space * 8, 2F);
        }
    }

    @Override
    public float getTextLineSpacing() {
        return lineSpace;
    }

    @Override
    public void setTextBold(boolean flag) {
        textView.getPaint().setFakeBoldText(flag);
        textView.setText(textView.getText());
    }

    @Override
    public boolean getTextBold() {
        return textView.getPaint().isFakeBoldText();
    }

    @Override
    public void setTextUnderline(boolean flag) {
        textView.getPaint().setUnderlineText(flag);
        textView.setText(textView.getText());
    }

    @Override
    public boolean getTextUnderline() {
        return textView.getPaint().isUnderlineText();
    }

    @Override
    public void setTextSkewX(float f) {
        textView.getPaint().setTextSkewX(f);
        textView.setText(textView.getText());
    }

    @Override
    public float getTextSkewX() {
        return textView.getPaint().getTextSkewX();
    }


    @Override
    public void setExcelData(List<String> excelData) {
        this.excelData = excelData;
    }

    @Override
    public List<String> getExcelData() {
        return excelData == null ? new ArrayList<>() : excelData;
    }

    public boolean hasExcelData() {
        return excelData != null && excelData.size() > 0;
    }


    @Override
    public TextItemView copy() {
        TextItemView copy = (TextItemView) super.copy();
        copy.excelData = excelData;
        copy.excelPosition = excelPosition;
        copy.textFont = textFont;
        copy.setTextStyle(textFont);
        copy.setText(getText());
        copy.setTextSize(getTextSize());
        copy.setTextGravity(getTextGravity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            copy.setTextLetterSpacing(getTextLetterSpacing());
        }
        copy.setTextLineSpacing(lineSpace);
        copy.setTextBold(getTextBold());
        copy.setTextSkewX(getTextSkewX());
        copy.setTextUnderline(getTextUnderline());
        return copy;
    }

    @SuppressLint("NewApi")
    public BaseItemModule toBean() {
        BaseItemModule itemModule = super.toBean();
        itemModule.setTagType(1);
        itemModule.setString(excelData.size() > 0 ? excelData.get(0) : getText());
        itemModule.setTextSize(TranslateUtils.getServiceTextSize(TranslateUtils.getPositionFromLocal(getTextSize())));
        itemModule.setTextFont(textFont);
        //1 ,1.5 ,2.0
        itemModule.setLineSpacing(getTextLineSpacing());
        itemModule.setFontSpacing(getTextLetterSpacing());
        StringBuilder excelString = new StringBuilder();
        for (int i = 0; i < excelData.size(); i++) {
            excelString.append(excelData.get(i));
            if (i < excelData.size() - 1) {
                excelString.append(":excel:");
            }
        }
        itemModule.setExcelString(excelString.toString());
        switch (textView.getGravity()) {
            case Gravity.START | Gravity.CENTER_VERTICAL:
                itemModule.setTextAlignment(0);
                break;
            case Gravity.CENTER:
                itemModule.setTextAlignment(1);
                break;
            case Gravity.END | Gravity.CENTER_VERTICAL:
                itemModule.setTextAlignment(2);
                break;
        }
//        itemModule.setFakeBoldText(getTextBold());
//        itemModule.setTextSkewX(getTextSkewX() != 0);
//        itemModule.setUnderlineText(getTextUnderline());
        itemModule.setFontProperty(getFont());
        return itemModule;
    }

    private int getFont() {
        int value = 0;
        if (!getTextBold() && getTextSkewX() == 0 && !getTextUnderline()) {
            value = 0;
        } else if (!getTextBold() && getTextSkewX() != 0 && !getTextUnderline()) {
            //斜体
            value = 1;
        } else if (getTextBold() && getTextSkewX() == 0 && !getTextUnderline()) {
            //加粗
            value = 2;
        } else if (!getTextBold() && getTextSkewX() == 0 && getTextUnderline()) {
            //下划线
            value = 3;
        } else if (getTextBold() && getTextSkewX() != 0 && !getTextUnderline()) {
            //加粗/斜体
            value = 4;
        } else if (getTextBold() && getTextSkewX() == 0 && getTextUnderline()) {
            //加粗/下划线
            value = 5;
        } else if (!getTextBold() && getTextSkewX() != 0 && getTextUnderline()) {
            //斜体/下划线
            value = 6;
        } else if (getTextBold() && getTextSkewX() != 0 && getTextUnderline()) {
            //斜体/下划线/粗体
            value = 7;
        }
        return value;
    }

    private void setFont(int value) {
        switch (value) {
            case 0:
                setTextBold(false);
                setTextSkewX(0f);
                setTextUnderline(false);
                break;
            case 1:
                setTextBold(false);
                setTextSkewX(-0.5f);
                setTextUnderline(false);
                break;
            case 2:
                setTextBold(true);
                setTextSkewX(0f);
                setTextUnderline(false);
                break;
            case 3:
                setTextBold(false);
                setTextSkewX(0f);
                setTextUnderline(true);
                break;
            case 4:
                setTextBold(true);
                setTextSkewX(-0.5f);
                setTextUnderline(false);
                break;
            case 5:
                setTextBold(true);
                setTextSkewX(0f);
                setTextUnderline(true);
                break;
            case 6:
                setTextBold(false);
                setTextSkewX(-0.5f);
                setTextUnderline(true);
                break;
            case 7:
                setTextBold(true);
                setTextSkewX(-0.5f);
                setTextUnderline(true);
                break;
        }
    }

    public TextItemView fromBean(BaseItemModule itemModule) {
        super.fromBean(itemModule);
        if (itemModule.getExcelString() != null && itemModule.getExcelString().contains(":excel:")) {
            setExcelData(Arrays.asList(itemModule.getExcelString().split(":excel:")));
        }
        if (excelData == null || excelData.size() <= 0) {
            setText(itemModule.getString());
        } else {
            setText(excelData.get(0));
        }
        setTextStyle(itemModule.getTextFont());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, TranslateUtils.getLocationTextSize(TranslateUtils.getPositionFromService(itemModule.getTextSize())));
        switch (itemModule.getTextAlignment()) {
            case 0:
                setTextGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                break;
            case 1:
                setTextGravity(Gravity.CENTER);
                break;
            case 2:
                setTextGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                break;
        }
        this.setTextLineSpacing(itemModule.getLineSpacing());
        this.setTextLetterSpacing(itemModule.getFontSpacing());
//        this.setTextBold(itemModule.isFakeBoldText());
//        this.setTextUnderline(itemModule.isUnderlineText());
//        this.setTextSkewX(itemModule.isTextSkewX() ? -0.5f : 0f);
        setFont(itemModule.getFontProperty());
        getLayoutParams().height = LayoutParams.WRAP_CONTENT;
        this.setLayoutParams(getLayoutParams());
        return this;
    }

    @Override
    public void onUpdate(int position) {
        if (excelData != null) {
            if (excelData.size() > 0) {
                if (position < excelData.size()) {
                    setVisibility(VISIBLE);
                    excelPosition = position;
                    textView.setText(excelData.get(position));
                    onViewLiveCycleListener.onUpdate((excelData.get(position)));
                }else{
                    setVisibility(GONE);
                }
            }
        }
    }

}
