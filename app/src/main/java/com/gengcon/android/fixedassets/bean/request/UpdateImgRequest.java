package com.gengcon.android.fixedassets.bean.request;

public class UpdateImgRequest {

    int id;
    String base64;

    public UpdateImgRequest(String base64, int id) {
        this.id = id;
        this.base64 = base64;
    }

}
