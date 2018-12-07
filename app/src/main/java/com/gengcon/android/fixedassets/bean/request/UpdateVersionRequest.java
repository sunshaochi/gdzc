package com.gengcon.android.fixedassets.bean.request;

public class UpdateVersionRequest {

    String versionName;
    boolean isUpdate;

    public UpdateVersionRequest(String versionName, boolean isUpdate) {
        this.versionName = versionName;
        this.isUpdate = isUpdate;
    }

}
