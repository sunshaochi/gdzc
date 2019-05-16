package com.gengcon.android.fixedassets.module.inventory.view;

import com.gengcon.android.fixedassets.bean.result.ResultInventory;
import com.gengcon.android.fixedassets.module.base.Iview;

public interface InventoryListView extends Iview {

    void showOffnetList(ResultInventory resultInventory);
}
