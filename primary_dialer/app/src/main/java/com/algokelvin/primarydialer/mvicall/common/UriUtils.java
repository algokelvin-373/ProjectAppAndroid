package com.algokelvin.primarydialer.mvicall.common;

import android.net.Uri;
import android.provider.ContactsContract;

import java.util.List;

/**
 * Utility methods for dealing with URIs.
 */
public class UriUtils {
    /** Static helper, not instantiable. */
    private UriUtils() {}

    /** Checks whether two URI are equal, taking care of the case where either is null. */
    public static boolean areEqual(Uri uri1, Uri uri2) {
        if (uri1 == null && uri2 == null) {
            return true;
        }
        if (uri1 == null || uri2 == null) {
            return false;
        }
        return uri1.equals(uri2);
    }

}
