package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Phone;

/**
 * Represents a phone data item, wrapping the columns in
 * {@link Phone}.
 */
public class PhoneDataItem extends DataItem {

    public static final String KEY_FORMATTED_PHONE_NUMBER = "formattedPhoneNumber";

    /* package */ PhoneDataItem(ContentValues values) {
        super(values);
    }

    public String getNumber() {
        return getContentValues().getAsString(Phone.NUMBER);
    }

    /**
     * Returns the normalized phone number in E164 format.
     */
    public String getNormalizedNumber() {
        return getContentValues().getAsString(Phone.NORMALIZED_NUMBER);
    }

    public String getFormattedPhoneNumber() {
        return getContentValues().getAsString(KEY_FORMATTED_PHONE_NUMBER);
    }

    public String getLabel() {
        return getContentValues().getAsString(Phone.LABEL);
    }

    public void computeFormattedPhoneNumber(String defaultCountryIso) {
        final String phoneNumber = getNumber();
        if (phoneNumber != null) {
            final String formattedPhoneNumber = PhoneNumberUtilsCompat.formatNumber(phoneNumber,
                    getNormalizedNumber(), defaultCountryIso);
            getContentValues().put(KEY_FORMATTED_PHONE_NUMBER, formattedPhoneNumber);
        }
    }

    /**
     * Returns the formatted phone number (if already computed using {@link
     * #computeFormattedPhoneNumber}). Otherwise this method returns the unformatted phone number.
     */
    @Override
    public String buildDataStringForDisplay(Context context, DataKind kind) {
        final String formatted = getFormattedPhoneNumber();
        if (formatted != null) {
            return formatted;
        } else {
            return getNumber();
        }
    }
}
