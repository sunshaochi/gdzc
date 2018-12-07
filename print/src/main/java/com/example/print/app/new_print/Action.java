package com.example.print.app.new_print;

import com.example.print.app.new_print.base.BaseItemView;
import com.example.print.app.new_print.module.BaseItemModule;

public class Action {

    private BaseItemModule baseItemModule;
    private BaseItemView baseItemView;
    private   int type;

    public Action(BaseItemView baseItemView, int type) {
        this.baseItemView = baseItemView;
        this.type = type;
    }

    public Action( BaseItemView baseItemView, int type,BaseItemModule baseItemModule) {
        this.baseItemModule = baseItemModule;
        this.baseItemView = baseItemView;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Action(BaseItemView baseItemView, BaseItemModule baseItemModule) {
        this.baseItemModule = baseItemModule;
        this.baseItemView = baseItemView;
    }

    public BaseItemModule getBaseItemModule() {
        return baseItemModule;
    }

    public void setBaseItemModule(BaseItemModule baseItemModule) {
        this.baseItemModule = baseItemModule;
    }

    public BaseItemView getBaseItemView() {
        return baseItemView;
    }

    public void setBaseItemView(BaseItemView baseItemView) {
        this.baseItemView = baseItemView;
    }
}
