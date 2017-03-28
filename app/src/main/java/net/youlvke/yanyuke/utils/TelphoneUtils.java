package net.youlvke.yanyuke.utils;

import java.util.regex.Pattern;

/**
 *     desc  : 正则相关工具类
 */
public class TelphoneUtils {

    private TelphoneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    /**
     * 验证手机号（精确）
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileExact(CharSequence input) {
        return isMatch("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$", input);
    }
    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    public static String subTel(String number){
        if (number !=null&& number.length()== 11){
            StringBuffer buffer = new StringBuffer(number);
            StringBuffer replace = buffer.replace(3, 7, "****");
            return replace.toString();
        }
        return null;
    }

}