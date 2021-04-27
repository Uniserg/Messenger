package com.serguni.messenger.utils;

import java.util.regex.Pattern;

public class RegExValidUtil {
    private static final String STANDARD_REGEX = "^[^%\"';:]*$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_]+@([a-zA-Z]{2,10}[.]){1,3}(com|by|ru|cc|net|ws)$";
    private static final String KEY_REGEX = "^([A-Za-z0-9]){5}$";
    private static final String PHONE_NUMBER_REGEX = "^[(+7)8]+([0-9]{10})$";

    public static boolean checkStandard(String text) { return Pattern.matches(STANDARD_REGEX, text); }

    public static boolean checkEmail(String email) { return Pattern.matches(EMAIL_REGEX, email); }

    public static boolean checkKey(String key) { return Pattern.matches(KEY_REGEX, key); }

    public static boolean checkPhoneNumber(String phoneNumber) { return Pattern.matches(PHONE_NUMBER_REGEX, phoneNumber); }

    public static String getEmailRegex() { return EMAIL_REGEX; }

    public static String getPasswordRegex() { return KEY_REGEX; }

    public static String getPhoneNumberRegex() { return PHONE_NUMBER_REGEX; }

    public static String getStandardRegex() { return STANDARD_REGEX; }
}
