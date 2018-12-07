package com.example.print.app.new_print.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 选中
 */
public class SelectedViewManager {
    private static SelectedViewManager manager;

    public static SelectedViewManager getDefault() {
        if (manager == null) {
            manager = new SelectedViewManager();
        }
        return manager;
    }

    private SelectedViewManager() {

    }


    private List<BaseItemView> baseItemViews = new ArrayList<>();

    public void register(BaseItemView baseItemView) {
        baseItemViews.add(baseItemView);
    }

    void unRegister(BaseItemView baseItemView) {
        baseItemViews.remove(baseItemView);
    }

    void postChanger(boolean isSelected) {
        for (BaseItemView baseItemView : baseItemViews) {
            baseItemView.setSelected(isSelected);
        }
    }

    void postChanger(BaseItemView itemView, boolean isSelected) {
        for (BaseItemView baseItemView : baseItemViews) {
            if (!baseItemView.getUuid().equals(itemView.getUuid())) {
                baseItemView.setSelected(isSelected);
            }
        }
    }
}
