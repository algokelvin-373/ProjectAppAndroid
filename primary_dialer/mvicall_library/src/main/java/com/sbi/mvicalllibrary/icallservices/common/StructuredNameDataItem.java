package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts.Data;

/**
 * Represents a structured name data item, wrapping the columns in
 * {@link StructuredName}.
 */
public class StructuredNameDataItem extends DataItem {

    public StructuredNameDataItem() {
        super(new ContentValues());
        getContentValues().put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
    }

    /* package */ StructuredNameDataItem(ContentValues values) {
        super(values);
    }

    public String getDisplayName() {
        return getContentValues().getAsString(StructuredName.DISPLAY_NAME);
    }

    public void setDisplayName(String name) {
        getContentValues().put(StructuredName.DISPLAY_NAME, name);
    }

    public String getGivenName() {
        return getContentValues().getAsString(StructuredName.GIVEN_NAME);
    }

    public String getFamilyName() {
        return getContentValues().getAsString(StructuredName.FAMILY_NAME);
    }

    public String getPrefix() {
        return getContentValues().getAsString(StructuredName.PREFIX);
    }

    public String getMiddleName() {
        return getContentValues().getAsString(StructuredName.MIDDLE_NAME);
    }

    public String getSuffix() {
        return getContentValues().getAsString(StructuredName.SUFFIX);
    }

    public String getPhoneticGivenName() {
        return getContentValues().getAsString(StructuredName.PHONETIC_GIVEN_NAME);
    }

    public String getPhoneticMiddleName() {
        return getContentValues().getAsString(StructuredName.PHONETIC_MIDDLE_NAME);
    }

    public String getPhoneticFamilyName() {
        return getContentValues().getAsString(StructuredName.PHONETIC_FAMILY_NAME);
    }

    public String getFullNameStyle() {
        return getContentValues().getAsString(StructuredName.FULL_NAME_STYLE);
    }

    public void setPhoneticFamilyName(String name) {
        getContentValues().put(StructuredName.PHONETIC_FAMILY_NAME, name);
    }

    public void setPhoneticMiddleName(String name) {
        getContentValues().put(StructuredName.PHONETIC_MIDDLE_NAME, name);
    }

    public void setPhoneticGivenName(String name) {
        getContentValues().put(StructuredName.PHONETIC_GIVEN_NAME, name);
    }

    public boolean isSuperPrimary() {
        final ContentValues contentValues = getContentValues();
        return contentValues == null || !contentValues.containsKey(StructuredName.IS_SUPER_PRIMARY)
                ? false : contentValues.getAsBoolean(StructuredName.IS_SUPER_PRIMARY);
    }
}
