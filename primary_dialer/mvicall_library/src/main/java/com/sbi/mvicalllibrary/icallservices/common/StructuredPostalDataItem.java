package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentValues;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;

/**
 * Represents a structured postal data item, wrapping the columns in
 * {@link StructuredPostal}.
 */
public class StructuredPostalDataItem extends DataItem {

    /* package */ StructuredPostalDataItem(ContentValues values) {
        super(values);
    }

    public String getFormattedAddress() {
        return getContentValues().getAsString(StructuredPostal.FORMATTED_ADDRESS);
    }

    public String getLabel() {
        return getContentValues().getAsString(StructuredPostal.LABEL);
    }

    public String getStreet() {
        return getContentValues().getAsString(StructuredPostal.STREET);
    }

    public String getPOBox() {
        return getContentValues().getAsString(StructuredPostal.POBOX);
    }

    public String getNeighborhood() {
        return getContentValues().getAsString(StructuredPostal.NEIGHBORHOOD);
    }

    public String getCity() {
        return getContentValues().getAsString(StructuredPostal.CITY);
    }

    public String getRegion() {
        return getContentValues().getAsString(StructuredPostal.REGION);
    }

    public String getPostcode() {
        return getContentValues().getAsString(StructuredPostal.POSTCODE);
    }

    public String getCountry() {
        return getContentValues().getAsString(StructuredPostal.COUNTRY);
    }
}
