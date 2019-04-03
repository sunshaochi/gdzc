package com.gengcon.android.fixedassets.common.module.checkphone;

import com.gengcon.android.fixedassets.module.base.Iview;
import com.gengcon.android.fixedassets.bean.result.ForgetPwd;

public interface CheckPhoneCodeView extends Iview {
    void checkPhoneCode();
    void checkForgetPhoneCode(ForgetPwd forgetPwd);
}
