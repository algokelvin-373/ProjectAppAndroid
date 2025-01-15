package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.text.TextUtils;

/**
 * Represents a relation data item, wrapping the columns in
 * {@link Relation}.
 */
public class RelationDataItem extends DataItem {

    /* package */ RelationDataItem(ContentValues values) {
        super(values);
    }

    public String getName() {
        return getContentValues().getAsString(Relation.NAME);
    }

    public String getLabel() {
        return getContentValues().getAsString(Relation.LABEL);
    }

    @Override
    public boolean shouldCollapseWith(DataItem t, Context context) {
        if (!(t instanceof RelationDataItem) || mKind == null || t.getDataKind() == null) {
            return false;
        }
        final RelationDataItem that = (RelationDataItem) t;
        // Relations can have different types (assistant, father) but have the same name
        if (!TextUtils.equals(getName(), that.getName())) {
            return false;
        } else if (!hasKindTypeColumn(mKind) || !that.hasKindTypeColumn(that.getDataKind())) {
            return hasKindTypeColumn(mKind) == that.hasKindTypeColumn(that.getDataKind());
        } else // Check if custom types are not the same
            if (getKindTypeColumn(mKind) != that.getKindTypeColumn(that.getDataKind())) {
            return false;
        } else
                return getKindTypeColumn(mKind) != Relation.TYPE_CUSTOM || TextUtils.equals(getLabel(), that.getLabel());
    }
}
