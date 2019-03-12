package com.gengcon.android.fixedassets.bean.result;

import java.io.Serializable;

public class ContactUs extends InvalidBean implements Serializable {


    /**
     * email : zhanglei.md@jingchenyun.com
     * tel : 18971396951 (温)
     */

    private String email;
    private String csd_tel;
    private String official_tel ;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCsd_tel() {
        return csd_tel;
    }

    public void setCsd_tel(String csd_tel) {
        this.csd_tel = csd_tel;
    }

    public String getOfficial_tel() {
        return official_tel;
    }

    public void setOfficial_tel(String official_tel) {
        this.official_tel = official_tel;
    }
}
