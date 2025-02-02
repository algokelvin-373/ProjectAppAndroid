package com.algokelvin.primarydialer.mvicall.common;

import android.util.Log;

/**
 * This class wraps several PhoneNumberUtil calls and TelephonyManager calls. Some of them are
 * the same as the ones in the framework's code base. We can remove those once they are part of
 * the public API.
 */
public class PhoneNumberHelper {

    private static final String LOG_TAG = PhoneNumberHelper.class.getSimpleName();

    /**
     * Determines if the specified number is actually a URI (i.e. a SIP address) rather than a
     * regular PSTN phone number, based on whether or not the number contains an "@" character.
     *
     * @param number Phone number
     * @return true if number contains @
     *
     * TODO: Remove if PhoneNumberUtils.isUriNumber(String number) is made public.
     */
    public static boolean isUriNumber(String number) {
        // Note we allow either "@" or "%40" to indicate a URI, in case
        // the passed-in string is URI-escaped.  (Neither "@" nor "%40"
        // will ever be found in a legal PSTN number.)
        return number != null && (number.contains("@") || number.contains("%40"));
    }

    /**
     * @return the "username" part of the specified SIP address, i.e. the part before the "@"
     * character (or "%40").
     *
     * @param number SIP address of the form "username@domainname" (or the URI-escaped equivalent
     * "username%40domainname")
     *
     * TODO: Remove if PhoneNumberUtils.getUsernameFromUriNumber(String number) is made public.
     */
    public static String getUsernameFromUriNumber(String number) {
        // The delimiter between username and domain name can be
        // either "@" or "%40" (the URI-escaped equivalent.)
        int delimiterIndex = number.indexOf('@');
        if (delimiterIndex < 0) {
            delimiterIndex = number.indexOf("%40");
        }
        if (delimiterIndex < 0) {
            Log.w(LOG_TAG,
                    "getUsernameFromUriNumber: no delimiter found in SIP addr '" + number + "'");
            return number;
        }
        return number.substring(0, delimiterIndex);
    }

    public static String formatNumber(String number, String normalizedNumber, String currentCountryIso) {
        return null;
    }
}
