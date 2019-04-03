package com.gengcon.android.fixedassets.module.inventory.view;

import com.gengcon.android.fixedassets.module.base.Iview;
import com.gengcon.android.fixedassets.bean.result.Bean;

public interface UploadInventoryView extends Iview {

    void uploadResult(Bean resultBean);

    void contractExpire();
}
