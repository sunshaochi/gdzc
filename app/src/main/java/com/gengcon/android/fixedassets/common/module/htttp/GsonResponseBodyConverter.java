package com.gengcon.android.fixedassets.common.module.htttp;

import com.gengcon.android.fixedassets.bean.result.Bean;
import com.gengcon.android.fixedassets.bean.result.ErrorBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Bean httpResult = gson.fromJson(response, Bean.class);
        if (httpResult.getCode().equals("CODE_200") || httpResult.getCode().equals("CODE_401")) {
            return gson.fromJson(response, type);
        } else {
            ErrorBean errorResponse = gson.fromJson(response, ErrorBean.class);
            throw new ResultException(errorResponse.getCode(), errorResponse.getMsg());
        }
    }
}

