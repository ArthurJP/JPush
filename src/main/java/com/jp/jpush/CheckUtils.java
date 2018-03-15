package com.jp.jpush;

import org.jetbrains.annotations.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {
    @Contract(value = "null -> true", pure = true)
    private static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        return s.trim().length() == 0;
    }

    /**
     * 采用严格验证，只要有一项为null，直接返回null
     */
    @Contract(value = "null -> true", pure = true)
    private static boolean isEmpty(List<String> s) {
        boolean result = false;
        for (String x : s) {
            if (isEmpty( x ))
                result = true;
        }
        return result;
    }

    /**
     * 只能以 “+” 或者 数字开头；后面的内容只能包含 “-” 和 数字。
     */
    private final static String MOBILE_NUMBER_CHARS = "^[+0-9][-0-9]{1,}$";
    private final static String TAG_ALIA_CHARS = "^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$";

    @Contract(value = "null ,null ->false", pure = true)
    private static boolean match(String s, String regex) {
        Pattern p = Pattern.compile( regex );
        Matcher m = p.matcher( s );
        return m.matches();
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isValidMobileNumber(String s) {
        if (isEmpty( s )) return false;
        return match( s, MOBILE_NUMBER_CHARS );
    }

    // 校验Tag Alias 只能是数字,英文字母和中文
    @Contract(value = "null ->false", pure = true)
    static boolean isValidTagsOrAlias(String s) {
        if (isEmpty( s ))
            return false;
        return match( s, TAG_ALIA_CHARS );
    }

    @Contract(value = "null ->false", pure = true)
    static boolean isValidTagsOrAlias(List<String> s) {
//        null 会在这里返回，但是contract并没有检测出来
        if (isEmpty( s ))
            return false;
        else {
            for (String x : s) {
                if (!match( x, TAG_ALIA_CHARS )) return false;
            }
            return true;
        }
    }

}
