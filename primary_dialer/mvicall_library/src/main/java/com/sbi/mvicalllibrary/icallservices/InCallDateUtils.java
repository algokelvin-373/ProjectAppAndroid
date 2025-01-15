package com.sbi.mvicalllibrary.icallservices;

import android.content.Context;
import android.content.res.Resources;

import com.sbi.mvicalllibrary.R;


public class InCallDateUtils {

    public static String formatDuration(Context context, long millis) {
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        int elapsedSeconds = (int) (millis / 1000);
        if (elapsedSeconds >= 3600) {
            hours = elapsedSeconds / 3600;
            elapsedSeconds -= hours * 3600;
        }
        if (elapsedSeconds >= 60) {
            minutes = elapsedSeconds / 60;
            elapsedSeconds -= minutes * 60;
        }
        seconds = elapsedSeconds;

        final Resources res = context.getResources();
        StringBuilder duration = new StringBuilder();
        try {
            if (hours > 0) {
                duration.append(res.getQuantityString(R.plurals.duration_hours, hours, hours));
            }
            if (minutes > 0) {
                if (hours > 0) {
                    duration.append(' ');
                }
                duration.append(res.getQuantityString(R.plurals.duration_minutes, minutes, minutes));
            }
            if (seconds > 0) {
                if (hours > 0 || minutes > 0) {
                    duration.append(' ');
                }
                duration.append(res.getQuantityString(R.plurals.duration_seconds, seconds, seconds));
            }
        } catch (Resources.NotFoundException e) {
            // Ignore; plurals throws an exception for an untranslated quantity for a given locale.
            return null;
        }
        return duration.toString();
    }
}
