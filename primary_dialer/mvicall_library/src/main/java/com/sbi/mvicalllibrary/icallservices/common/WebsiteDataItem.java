package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.provider.ContactsContract.CommonDataKinds.Website;

/**
 * Represents a website data item, wrapping the columns in
 * {@link Website}.
 */
public class WebsiteDataItem extends DataItem {

    /* package */ WebsiteDataItem(ContentValues values) {
        super(values);
    }

    public String getUrl() {
        return getContentValues().getAsString(Website.URL);
    }

    public String getLabel() {
        return getContentValues().getAsString(Website.LABEL);
    }
}
