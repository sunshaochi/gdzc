package com.gengcon.android.fixedassets.view;

import com.gengcon.android.fixedassets.base.Iview;
import com.gengcon.android.fixedassets.bean.result.MessageBean;
import com.gengcon.android.fixedassets.bean.result.PersonalBean;

public interface PersonalView extends Iview {
    void showPersonalData(PersonalBean data);
}
