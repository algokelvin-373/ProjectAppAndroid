package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.provider.ContactsContract.CommonDataKinds.Note;

/**
 * Represents a note data item, wrapping the columns in
 * {@link Note}.
 */
public class NoteDataItem extends DataItem {

    /* package */ NoteDataItem(ContentValues values) {
        super(values);
    }

    public String getNote() {
        return getContentValues().getAsString(Note.NOTE);
    }
}
