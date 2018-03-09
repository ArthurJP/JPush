package com.jp.jpush;

import org.jetbrains.annotations.*;

import org.apache.http.util.TextUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {
    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        return s.trim().length() == 0;
    }

    /**
     * 只能以 “+” 或者 数字开头；后面的内容只能包含 “-” 和 数字。
     */
    private final static String MOBILE_NUMBER_CHARS = "^[+0-9][-0-9]{1,}$";

    @Contract(value = "null -> false",pure = true)
    public static boolean isValidMobileNumber(String s) {
        if (TextUtils.isEmpty( s )) return false;
        Pattern p = Pattern.compile( MOBILE_NUMBER_CHARS );
        Matcher m = p.matcher( s );
        return m.matches();
    }

    // 校验Tag Alias 只能是数字,英文字母和中文
    @Contract(value = "null ->false", pure = true)
    static boolean isValidTagAndAlias(List<String> s) {
        if (s.isEmpty())
            return false;
        for (String x : s) {
            Pattern p = Pattern.compile( "^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$" );
            Matcher m = p.matcher( x );
            if (!m.matches()) {
                return false;
            }
        }
        return true;
    }

}
