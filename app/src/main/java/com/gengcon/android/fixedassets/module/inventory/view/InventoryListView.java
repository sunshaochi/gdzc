package com.gengcon.android.fixedassets.module.inventory.view;

import com.gengcon.android.fixedassets.module.base.Iview;
import com.gengcon.android.fixedassets.bean.result.ResultInventorys;

public interface InventoryListView extends Iview {

    void showInventorys(ResultInventorys resultInventorys);
}
