package com.gengcon.android.fixedassets.common.module.http;

import android.os.Build;
import android.util.Log;


import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public enum RxService {
    RETROFIT;
    private Retrofit mRetrofit;
    private static final int READ_TIMEOUT = 60;//读取超时时间,单位秒
    private static final int CONN_TIMEOUT = 50;//连接超时时间,单位秒

    private Interceptor mHeadInterceptor = new Interceptor() {//头信息
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间

            String method = request.method();
            if ("POST".equals(method)) {
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    Log.e("NET_RELATIVE", "intercept: ");
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    Log.d("NET_RELATIVE", String.format("发送请求 %s on %s %n%s %nRequestParams:{%s}",
                            request.url(), chain.connection(), request.headers(), sb.toString()));
                }
            } else {
                Log.d("NET_RELATIVE", String.format("发送请求 %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));
            }
            Response response = chain.proceed(initHead(chain));
            long t2 = System.nanoTime();//收到响应的时间
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Log.d("NET_RELATIVE",
                    String.format("接收响应: [%s] %n返回json:【%s】 %.1fms %n%s",
                            response.request().url(),
                            responseBody.string(),
                            (t2 - t1) / 1e6d,
                            response.headers()
                    ));

            return response;
        }
    };

    public Retrofit createRetrofit() {
        if (mRetrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()//初始化一个client,不然retrofit会自己默认添加一个
                    .addInterceptor(mHeadInterceptor)
                    .connectTimeout(CONN_TIMEOUT, TimeUnit.MINUTES)//设置连接时间为50s
                    .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)//设置读取时间为一分钟
                    .build();

            mRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(URL.BASE_URL)
                    .addConverterFactory(ResponseConverterFactory.create())//返回值为Gson的支持(以实体类返回)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//返回值为Oservable<T>的支持
                    .build();
        }

        return mRetrofit;
    }

    public Request initHead(Interceptor.Chain chain) {
        Request.Builder mBuilder = chain.request().newBuilder();
        //系统级请求参数
//        Log.e("RxService", "initHead: " + (String) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.TOKEN, ""));
        mBuilder.addHeader("Authorization", (String) SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.TOKEN, ""));
        mBuilder.removeHeader("User-Agent").addHeader("User-Agent", getUserAgent());
        Request mRequest = mBuilder.build();
        return mRequest;
    }

    private static String getUserAgent() {

        String userAgent = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

//            try {
//
//                userAgent = WebSettings.getDefaultUserAgent(GApplication.getInstance().getBaseContext());
//
//            } catch (Exception e) {

                userAgent = System.getProperty("http.agent");

//            }
//
//        } else {
//
//            userAgent = System.getProperty("http.agent");
//
        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0, length = userAgent.length(); i < length; i++) {

            char c = userAgent.charAt(i);

            if (c <= '\u001f' || c >= '\u007f') {

                sb.append(String.format("\\u%04x", (int) c));

            } else {

                sb.append(c);

            }

        }

        return sb.toString();

    }

}
