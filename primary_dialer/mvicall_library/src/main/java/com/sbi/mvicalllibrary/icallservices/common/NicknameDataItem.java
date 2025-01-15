package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.provider.ContactsContract.CommonDataKinds.Nickname;

/**
 * Represents a nickname data item, wrapping the columns in
 * {@link Nickname}.
 */
public class NicknameDataItem extends DataItem {

    public NicknameDataItem(ContentValues values) {
        super(values);
    }

    public String getName() {
        return getContentValues().getAsString(Nickname.NAME);
    }

    public String getLabel() {
        return getContentValues().getAsString(Nickname.LABEL);
    }
}
