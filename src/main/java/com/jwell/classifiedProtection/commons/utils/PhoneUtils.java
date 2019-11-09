package com.jwell.classifiedProtection.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtils {

    public static boolean isPhoneNo(String phone) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }
}
