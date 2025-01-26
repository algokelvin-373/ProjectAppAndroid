package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.provider.ContactsContract.CommonDataKinds.SipAddress;

/**
 * Represents a sip address data item, wrapping the columns in
 * {@link SipAddress}.
 */
public class SipAddressDataItem extends DataItem {

    /* package */ SipAddressDataItem(ContentValues values) {
        super(values);
    }

    public String getSipAddress() {
        return getContentValues().getAsString(SipAddress.SIP_ADDRESS);
    }

    public String getLabel() {
        return getContentValues().getAsString(SipAddress.LABEL);
    }
}
