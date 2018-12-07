package com.gengcon.android.fixedassets.util;

import java.util.List;

public class PrinterSubString {

    public static void substring5030(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                list.set(i, intercept(list.get(i), 32));
            } else if (list.get(i).indexOf("资产名称") != -1) {
                list.set(i,intercept(list.get(i),24 + 9));
            } else {
                list.set(i,intercept(list.get(i),12 + 9));
            }
        }
    }

    public static void substring7050(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                list.set(i, intercept(list.get(i), 32 + 9));
            } else {
                list.set(i,intercept(list.get(i),20 + 9));
            }
        }
    }

    private static String intercept(String str, int maxLenght) {
        int len = 0;
        int index;
        char[] chars = str.toCharArray();
        for (index = 0; index < chars.length; index++) {
            len += isChinese(chars[index]) ? 2 : 1;
            if (len > maxLenght) {
                break;
            }
        }
        return str.substring(0, index);
    }

    private static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

}
