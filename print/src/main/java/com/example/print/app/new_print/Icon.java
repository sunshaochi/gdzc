package com.example.print.app.new_print;


import androidx.annotation.DrawableRes;
import com.example.print.app.R;

public enum Icon {
    TEXT("文本", R.drawable.selector_edit_text)
    ,ONE_CODE("一维码", R.drawable.selector_onecode)
    ,TWO_CODE("二维码", R.drawable.selector_qr)
    ,FORM("表格", R.drawable.selector_form)
    ,PICTURE("图片", R.drawable.selector_pic)
    ,SCAN("扫描", R.drawable.selector_scal)
    ,NUMBER("流水号", R.drawable.selector_no)
    ,EXCEL("Excel导入", R.drawable.selector_excel)
    ,TIME("时间", R.drawable.selector_time)
    ,SHAPE("形状", R.drawable.selector_shape)
    ,LOGO("Logo", R.drawable.selector_logo)
    ,LINE("线条", R.drawable.selector_line)
    ,DISTINGUISH("识别", R.drawable.selector_distinguish);

    public String name;
    public int icon;

    private Icon(String name, @DrawableRes int icon) {
        this.name = name;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }
}
