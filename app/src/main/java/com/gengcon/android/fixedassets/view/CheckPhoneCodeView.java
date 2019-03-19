package com.gengcon.android.fixedassets.view;

import com.gengcon.android.fixedassets.base.Iview;
import com.gengcon.android.fixedassets.bean.result.ForgetPwd;

public interface CheckPhoneCodeView extends Iview {
    void checkPhoneCode();
    void checkForgetPhoneCode(ForgetPwd forgetPwd);
}
