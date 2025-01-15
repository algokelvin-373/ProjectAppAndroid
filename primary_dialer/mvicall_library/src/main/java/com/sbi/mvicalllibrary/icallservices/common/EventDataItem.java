package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.text.TextUtils;

/**
 * Represents an event data item, wrapping the columns in
 * {@link Event}.
 */
public class EventDataItem extends DataItem {

    /* package */ EventDataItem(ContentValues values) {
        super(values);
    }

    public String getStartDate() {
        return getContentValues().getAsString(Event.START_DATE);
    }

    public String getLabel() {
        return getContentValues().getAsString(Event.LABEL);
    }

    @Override
    public boolean shouldCollapseWith(DataItem t, Context context) {
        if (!(t instanceof EventDataItem) || mKind == null || t.getDataKind() == null) {
            return false;
        }
        final EventDataItem that = (EventDataItem) t;
        // Events can be different (anniversary, birthday) but have the same start date
        if (!TextUtils.equals(getStartDate(), that.getStartDate())) {
            return false;
        } else if (!hasKindTypeColumn(mKind) || !that.hasKindTypeColumn(that.getDataKind())) {
            return hasKindTypeColumn(mKind) == that.hasKindTypeColumn(that.getDataKind());
        } else // Check if custom types are not the same
            if (getKindTypeColumn(mKind) != that.getKindTypeColumn(that.getDataKind())) {
            return false;
        } else
                return getKindTypeColumn(mKind) != Event.TYPE_CUSTOM || TextUtils.equals(getLabel(), that.getLabel());
    }
}
