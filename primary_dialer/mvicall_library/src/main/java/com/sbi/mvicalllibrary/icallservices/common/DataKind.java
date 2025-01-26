package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.content.Context;

import com.google.common.collect.Iterators;
import com.sbi.mvicalllibrary.icallservices.common.AccountType.EditField;
import com.sbi.mvicalllibrary.icallservices.common.AccountType.EditType;
import com.sbi.mvicalllibrary.icallservices.common.AccountType.StringInflater;

import java.text.SimpleDateFormat;
import java.util.List;


public final class DataKind {

    public static final String PSEUDO_MIME_TYPE_DISPLAY_NAME = "#displayName";
    public static final String PSEUDO_MIME_TYPE_PHONETIC_NAME = "#phoneticName";
    public static final String PSEUDO_COLUMN_PHONETIC_NAME = "#phoneticName";

    public String resourcePackageName;
    public String mimeType;
    public int titleRes;
    public int iconAltRes;
    public int iconAltDescriptionRes;
    public int weight;
    public boolean editable;

    public StringInflater actionHeader;
    public StringInflater actionAltHeader;
    public StringInflater actionBody;

    public String typeColumn;

    /**
     * Maximum number of values allowed in the list. -1 represents infinity.
     */
    public int typeOverallMax;

    public List<EditType> typeList;
    public List<EditField> fieldList;

    public ContentValues defaultValues;

    /**
     * If this is a date field, this specifies the format of the date when saving. The
     * date includes year, month and day. If this is not a date field or the date field is not
     * editable, this value should be ignored.
     */
    public SimpleDateFormat dateFormatWithoutYear;

    /**
     * If this is a date field, this specifies the format of the date when saving. The
     * date includes month and day. If this is not a date field, the field is not editable or
     * dates without year are not supported, this value should be ignored.
     */
    public SimpleDateFormat dateFormatWithYear;

    /**
     * The number of lines available for displaying this kind of data.
     * Defaults to 1.
     */
    public int maxLinesForDisplay;

    public DataKind() {
        maxLinesForDisplay = 1;
    }

    public DataKind(String mimeType, int titleRes, int weight, boolean editable) {
        this.mimeType = mimeType;
        this.titleRes = titleRes;
        this.weight = weight;
        this.editable = editable;
        this.typeOverallMax = -1;
        maxLinesForDisplay = 1;
    }

    public String getKindString(Context context) {
        return (titleRes == -1 || titleRes == 0) ? "" : context.getString(titleRes);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DataKind:");
        sb.append(" resPackageName=").append(resourcePackageName);
        sb.append(" mimeType=").append(mimeType);
        sb.append(" titleRes=").append(titleRes);
        sb.append(" iconAltRes=").append(iconAltRes);
        sb.append(" iconAltDescriptionRes=").append(iconAltDescriptionRes);
        sb.append(" weight=").append(weight);
        sb.append(" editable=").append(editable);
        sb.append(" actionHeader=").append(actionHeader);
        sb.append(" actionAltHeader=").append(actionAltHeader);
        sb.append(" actionBody=").append(actionBody);
        sb.append(" typeColumn=").append(typeColumn);
        sb.append(" typeOverallMax=").append(typeOverallMax);
        sb.append(" typeList=").append(toString(typeList));
        sb.append(" fieldList=").append(toString(fieldList));
        sb.append(" defaultValues=").append(defaultValues);
        sb.append(" dateFormatWithoutYear=").append(toString(dateFormatWithoutYear));
        sb.append(" dateFormatWithYear=").append(toString(dateFormatWithYear));

        return sb.toString();
    }

    public static String toString(SimpleDateFormat format) {
        return format == null ? "(null)" : format.toPattern();
    }

    public static String toString(Iterable<?> list) {
        if (list == null) {
            return "(null)";
        } else {
            return Iterators.toString(list.iterator());
        }
    }
}
