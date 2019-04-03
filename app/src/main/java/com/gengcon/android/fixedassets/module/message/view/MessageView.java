package com.gengcon.android.fixedassets.module.message.view;

import com.gengcon.android.fixedassets.module.base.Iview;
import com.gengcon.android.fixedassets.bean.result.MessageBean;

public interface MessageView extends Iview {
    void showMessage(MessageBean message);
    void showMoreMessage(MessageBean message);
}
