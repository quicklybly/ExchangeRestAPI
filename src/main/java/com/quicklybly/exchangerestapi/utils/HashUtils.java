package com.quicklybly.exchangerestapi.utils;

import java.security.SecureRandom;

public class HashUtils {
    private HashUtils() {
    }

    private static final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXY" +
            "Zabcdefghijklmnopqrstuvwxyz";

    private static final SecureRandom random = new SecureRandom();

    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return sb.toString();
    }
}
