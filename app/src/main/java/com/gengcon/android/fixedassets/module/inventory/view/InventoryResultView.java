package com.gengcon.android.fixedassets.module.inventory.view;

import com.gengcon.android.fixedassets.bean.result.ResultAsset;
import com.gengcon.android.fixedassets.module.base.Iview;

public interface InventoryResultView extends Iview {

    void showInventoryResult(ResultAsset resultAsset);

    void syncAssetSuccess();

    void syncAssetFailed(int type, String msg);

    void keepSonAuditSuccess();

    void keepSonAuditFailed(int type, String msg);
}
