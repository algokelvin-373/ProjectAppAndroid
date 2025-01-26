package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.provider.ContactsContract.CommonDataKinds.Email;

/**
 * Represents an email data item, wrapping the columns in
 * {@link Email}.
 */
public class EmailDataItem extends DataItem {

    /* package */ EmailDataItem(ContentValues values) {
        super(values);
    }

    public String getAddress() {
        return getContentValues().getAsString(Email.ADDRESS);
    }

    public String getDisplayName() {
        return getContentValues().getAsString(Email.DISPLAY_NAME);
    }

    public String getData() {
        return getContentValues().getAsString(Email.DATA);
    }

    public String getLabel() {
        return getContentValues().getAsString(Email.LABEL);
    }
}
