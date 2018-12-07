package com.example.print.app.new_print.time;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;

import com.example.print.app.new_print.module.BaseItemModule;
import com.example.print.app.new_print.text.TextItemView;
import com.example.print.app.util.TranslateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

@SuppressLint("SetTextI18n")
public class DateItemView extends TextItemView implements ITimeItem {
    private String dataType = "yyyy年MM月dd日";
    private String timeType = "无";
    private String data = "";
    private String time = "";

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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public static DateItemView create(Context context) {
        DateItemView textItemView = new DateItemView(context);
        textItemView.setSelected(true);
        Paint paint = new Paint();
        paint.setTextSize(TranslateUtils.getLocationTextSize(2));
        int width = (int) (paint.measureText("2019年12月12日") + textItemView.horizontalBitmapWidth);
        LayoutParams layoutParams = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
        textItemView.setLayoutParams(layoutParams);
        return textItemView;
    }

    public DateItemView(Context context) {
        super(context);
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        setData(dataFormat.format(new Date()));
        setText();
    }


    @Override
    public void setText() {
        if (data != null) {
            data = data.replace("无", "");
        }
        if (time != null) {
            time = time.replace("无", "");
        }
        textView.setText((data == null ? "" : data) + "  " + (time == null ? "" : time));
    }


    public DateItemView copy() {
        DateItemView copy = (DateItemView) super.copy();
        copy.dataType = dataType;
        copy.timeType = timeType;
        copy.data = data;
        copy.time = time;
        copy.setText();
        return copy;
    }

    public BaseItemModule toBean() {
        BaseItemModule itemModule = super.toBean();
        itemModule.setTagType(8);
        itemModule.setDateFormat1(dataType);
        itemModule.setDateFormat2(timeType);
        itemModule.setData(data);
        itemModule.setTime(time);
        itemModule.setString(data + " " + time);
        return itemModule;
    }

    public DateItemView fromBean(BaseItemModule itemModule) {
        super.fromBean(itemModule);
        this.setDataType(itemModule.getDateFormat1());
        this.setTimeType(itemModule.getDateFormat2());
        try {
            Date date = new SimpleDateFormat(itemModule.getDateFormat1() + itemModule.getDateFormat2(), Locale.CHINA).parse(itemModule.getString());
            SimpleDateFormat dataFm = new SimpleDateFormat(itemModule.getDateFormat1(), Locale.CHINA);
            SimpleDateFormat timeFm = new SimpleDateFormat(itemModule.getDateFormat2(), Locale.CHINA);
            this.setData(dataFm.format(date));
            this.setTime(timeFm.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public void onUpdate(int position) {
    }
}
