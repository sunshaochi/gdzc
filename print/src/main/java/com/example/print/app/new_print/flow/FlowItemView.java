package com.example.print.app.new_print.flow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;

import com.example.print.app.new_print.module.BaseItemModule;
import com.example.print.app.new_print.text.ITextItem;
import com.example.print.app.new_print.text.TextItemView;
import com.example.print.app.util.TranslateUtils;

@SuppressLint("SetTextI18n")
public class FlowItemView extends TextItemView implements IFlowItem, ITextItem {
    private String textBefore = "";
    private String textAfter = "";
    private String textStartNum = "";
    private int textStep = 1;


    public void setDefault() {
        textBefore = "";
        textAfter = "";
        textStartNum = "";
        textStep = 1;
    }


    public static TextItemView create(Context context) {
        FlowItemView flowItemView = new FlowItemView(context);
        flowItemView.setSelected(true);
        Paint paint = new Paint();
        paint.setTextSize(TranslateUtils.getLocationTextSize(2));
        int width = (int) (paint.measureText("请输入起始数据") + flowItemView.horizontalBitmapWidth);
        LayoutParams layoutParams = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
        flowItemView.setLayoutParams(layoutParams);
        return flowItemView;
    }

    public FlowItemView(Context context) {
        super(context);
        setHint("请输入起始数据");
    }


    @Override
    public void setText(int position) {
        if (TextUtils.isEmpty(textStartNum)) {
            textStartNum = "0";
        }
        textView.setText(textBefore + (Integer.parseInt(textStartNum) + position * textStep) + textAfter);
    }


    @Override
    public void setText() {
        setText(0);
    }

    @Override
    public void setTextBefore(String textBefore) {
        this.textBefore = textBefore;
    }

    @Override
    public String getTextBefore() {
        return textBefore;
    }

    @Override
    public void setTextAfter(String textAfter) {
        this.textAfter = textAfter;
    }

    @Override
    public String getTextAfter() {
        return textAfter;
    }

    @Override
    public void setTextStartNum(String startNum) {
        if (TextUtils.isEmpty(startNum)) {
            startNum = "0";
        }
        this.textStartNum = startNum;
    }

    @Override
    public String getTextStartNum() {
        return textStartNum;
    }

    @Override
    public void setTextStep(int step) {
        this.textStep = step;
    }

    @Override
    public int getTextStep() {
        return textStep;
    }


    @Override
    public FlowItemView copy() {
        FlowItemView copy = (FlowItemView) super.copy();
        copy.textBefore = textBefore;
        copy.textAfter = textAfter;
        copy.textStartNum = textStartNum;
        copy.textStep = textStep;
        return copy;
    }

    @Override
    public BaseItemModule toBean() {
        BaseItemModule itemModule = super.toBean();
        itemModule.setTagType(9);
        itemModule.setSerialPrefix(textBefore);
        itemModule.setSerialSuffix(textAfter);
        itemModule.setSerialStart(String.valueOf(textStartNum));
        itemModule.setIncreasesNum(String.valueOf(textStep));
        return itemModule;
    }


    @Override
    public FlowItemView fromBean(BaseItemModule itemModule) {
        super.fromBean(itemModule);
        this.textBefore = itemModule.getSerialPrefix();
        this.textAfter = itemModule.getSerialSuffix();
        this.textStartNum = itemModule.getSerialStart();
        this.textStep = TextUtils.isEmpty(itemModule.getIncreasesNum()) ? 0 : Integer.parseInt(itemModule.getIncreasesNum());
        return this;
    }

    @Override
    public void onUpdate(int position) {
//        setText(position);
    }
}
