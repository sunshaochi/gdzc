package com.gengcon.android.fixedassets.view;

import com.gengcon.android.fixedassets.base.Iview;
import com.gengcon.android.fixedassets.bean.result.InventoryR;
import com.gengcon.android.fixedassets.bean.result.MessageBean;

public interface MessageView extends Iview {
    void showMessage(MessageBean message);
}
