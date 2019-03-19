package com.gengcon.android.fixedassets.view;

import com.gengcon.android.fixedassets.base.Iview;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.result.InventoryDetail;
import com.gengcon.android.fixedassets.bean.result.InventoryHeadDetail;
import com.gengcon.android.fixedassets.bean.result.Bean;

import java.util.List;

public interface EditInventoryView extends Iview {

    void updateInventoryResult(Bean bean);

    void showInventoryResult(InventoryDetail inventoryDetail);

    void showInventoryMoreResult(InventoryDetail inventoryDetail);

    void initUsers(List<User> users);

    void initHeadDetail(InventoryHeadDetail headDetail);

    void contractExpire();

}
