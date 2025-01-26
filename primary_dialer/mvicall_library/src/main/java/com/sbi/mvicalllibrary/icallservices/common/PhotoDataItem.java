package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.provider.ContactsContract.Contacts.Photo;

/**
 * Represents a photo data item, wrapping the columns in
 * {@link Photo}.
 */
public class PhotoDataItem extends DataItem {

    /* package */ PhotoDataItem(ContentValues values) {
        super(values);
    }

    public Long getPhotoFileId() {
        return getContentValues().getAsLong(Photo.PHOTO_FILE_ID);
    }

    public byte[] getPhoto() {
        return getContentValues().getAsByteArray(Photo.PHOTO);
    }
}
