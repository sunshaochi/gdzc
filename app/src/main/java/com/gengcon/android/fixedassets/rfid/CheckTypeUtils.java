package com.gengcon.android.fixedassets.rfid;

import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * 判断是手机还是rfid设备
 */
public class CheckTypeUtils {

    public static boolean isPhoneOrEquipment(){

        String type=RgetPropeerty("persist.idata.app.code","unknown");
        if(TextUtils.isEmpty(type)||type.equals("unknown")){
          return false;//不是自己设备
        }else {
            return true;//是自己设备
        }

    }


    private static String RgetPropeerty(String key, String defaultValue) {
        String value = defaultValue;

        try {

            Class<?> c = Class.forName("android.os.SystemProperties");

            Method get = c.getMethod("get", String.class, String.class);

            value = (String)(get.invoke(c, key, "unknown" ));

        } catch (Exception e)
        {

            e.printStackTrace();

        }finally
        {
            return value;

        }
    }
}
