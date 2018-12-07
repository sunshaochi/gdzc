package com.example.print.app.new_print;


import com.example.print.app.R;

import androidx.annotation.DrawableRes;

public enum Function {
    DELETE(R.drawable.selector_delete,"删除"),
    SCALE_DOWN(R.drawable.selector_scale_down,"缩小"),
    SCALE_UP(R.drawable.selector_scale_up,"放大"),
    ROTATE(R.drawable.selector_rotate,"旋转"),
    MORE(R.drawable.selector_more,"单选"),
    REVOKE(R.drawable.selector_revoke,"撤销"),
    RECOVER(R.drawable.selector_recover,"恢复"),
    COPY(R.drawable.selector_copy,"复制"),
    LOOK(R.drawable.selector_look,"锁定"),
    TRANSLATE(R.drawable.selector_translate,"位移");
    public int icon;
    public String name;

    Function(@DrawableRes int icon, String name) {
        this.icon = icon;
        this.name = name;
    }
}
