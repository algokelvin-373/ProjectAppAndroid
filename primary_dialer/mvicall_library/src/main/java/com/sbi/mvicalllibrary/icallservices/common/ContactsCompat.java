
package com.sbi.mvicalllibrary.icallservices.common;

import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;

/**
 * Compatibility class for {@link Contacts}
 */
public class ContactsCompat {
    /**
     * Not instantiable.
     */
    private ContactsCompat() {
    }

    // TODO: Use N APIs
    private static final Uri ENTERPRISE_CONTENT_FILTER_URI =
            Uri.withAppendedPath(Contacts.CONTENT_URI, "filter_enterprise");

    // Copied from ContactsContract.Contacts#ENTERPRISE_CONTACT_ID_BASE, which is hidden.
    private static final long ENTERPRISE_CONTACT_ID_BASE = 1000000000;

    public static Uri getContentUri() {
        if (ContactsUtils.FLAG_N_FEATURE) {
            return ENTERPRISE_CONTENT_FILTER_URI;
        }
        return Contacts.CONTENT_FILTER_URI;
    }

    /**
     * Return {@code true} if a contact ID is from the contacts provider on the enterprise profile.
     */
    public static boolean isEnterpriseContactId(long contactId) {
        if (CompatUtils.isLollipopCompatible()) {
            return Contacts.isEnterpriseContactId(contactId);
        } else {
            // copied from ContactsContract.Contacts.isEnterpriseContactId
            return (contactId >= ENTERPRISE_CONTACT_ID_BASE) &&
                    (contactId < ContactsContract.Profile.MIN_ID);
        }
    }
}
