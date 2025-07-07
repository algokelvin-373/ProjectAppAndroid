package com.algokelvin.primarydialer.mvicall.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Locale;

/**
 * This class provides several TelephonyManager util functions.
 */
public class TelephonyManagerUtils {

    private static final String LOG_TAG = TelephonyManagerUtils.class.getSimpleName();

    /**
     * Gets the voicemail tag from Telephony Manager.
     * @param context Current application context
     * @return Voicemail tag, the alphabetic identifier associated with the voice mail number.
     */
    public static String getVoiceMailAlphaTag(Context context) {
        final TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") final String voiceMailLabel = telephonyManager.getVoiceMailAlphaTag();
        return voiceMailLabel;
    }

    /**
     * @return The ISO 3166-1 two letters country code of the country the user
     *         is in based on the network location. If the network location does not exist, fall
     *         back to the locale setting.
     */
    public static String getCurrentCountryIso(Context context, Locale locale) {
        // Without framework function calls, this seems to be the most accurate location service
        // we can rely on.
        final TelephonyManager telephonyManager =
            (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String countryIso = telephonyManager.getNetworkCountryIso().toUpperCase();

        if (countryIso == null) {
            countryIso = locale.getCountry();
            Log.w(LOG_TAG, "No CountryDetector; falling back to countryIso based on locale: "
                    + countryIso);
        }
        return countryIso;
    }
}
