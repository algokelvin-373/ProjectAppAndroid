package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.provider.ContactsContract.CommonDataKinds.Identity;

/**
 * Represents an identity data item, wrapping the columns in
 * {@link Identity}.
 */
public class IdentityDataItem extends DataItem {

    /* package */ IdentityDataItem(ContentValues values) {
        super(values);
    }

    public String getIdentity() {
        return getContentValues().getAsString(Identity.IDENTITY);
    }

    public String getNamespace() {
        return getContentValues().getAsString(Identity.NAMESPACE);
    }
}
