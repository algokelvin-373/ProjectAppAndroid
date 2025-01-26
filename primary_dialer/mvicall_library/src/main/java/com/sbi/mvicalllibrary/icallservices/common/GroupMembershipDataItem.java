package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;

/**
 * Represents a group memebership data item, wrapping the columns in
 * {@link GroupMembership}.
 */
public class GroupMembershipDataItem extends DataItem {

    /* package */ GroupMembershipDataItem(ContentValues values) {
        super(values);
    }

    public Long getGroupRowId() {
        return getContentValues().getAsLong(GroupMembership.GROUP_ROW_ID);
    }

    public String getGroupSourceId() {
        return getContentValues().getAsString(GroupMembership.GROUP_SOURCE_ID);
    }
}
