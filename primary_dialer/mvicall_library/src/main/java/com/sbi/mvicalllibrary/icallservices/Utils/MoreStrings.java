package com.sbi.mvicalllibrary.icallservices.Utils;

public class MoreStrings {

    public static String toSafeString(String value) {
        if (value == null) {
            return null;
        }

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            final char c = value.charAt(i);
            if (c == '-' || c == '@' || c == '.') {
                builder.append(c);
            } else {
                builder.append('x');
            }
        }
        return builder.toString();
    }

}
