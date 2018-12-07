package com.gengcon.android.fixedassets.view;

import com.gengcon.android.fixedassets.base.Iview;
import com.gengcon.android.fixedassets.bean.result.InventoryDetail;
import com.gengcon.android.fixedassets.bean.result.InventoryHeadDetail;

public interface InventoryDetailView extends Iview {

    void initDetail(InventoryDetail inventoryDetail);

    void initHeadDetail(InventoryHeadDetail headDetail);

}
