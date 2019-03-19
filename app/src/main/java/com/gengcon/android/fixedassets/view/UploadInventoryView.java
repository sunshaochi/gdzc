package com.gengcon.android.fixedassets.view;

import com.gengcon.android.fixedassets.base.Iview;
import com.gengcon.android.fixedassets.bean.result.Bean;

public interface UploadInventoryView extends Iview {

    void uploadResult(Bean resultBean);

    void contractExpire();
}
