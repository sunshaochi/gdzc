package com.gengcon.android.fixedassets.util;

public class StringIsDigitUtil {

    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }

        String regex = "^[a-z0-9]+$";
        boolean isRight = isDigit || isLetter;
        boolean right = isRight && str.matches(regex);
        return right;
    }
}
