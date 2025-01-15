package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentProviderOperation;

/**
 * This class is created for the purpose of compatibility and make the type of
 * ContentProviderOperation available on pre-M SDKs.
 */
public class CPOWrapper {
    private ContentProviderOperation mOperation;
    private int mType;

    public CPOWrapper(ContentProviderOperation builder, int type) {
        mOperation = builder;
        mType = type;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public ContentProviderOperation getOperation() {
        return mOperation;
    }

    public void setOperation(ContentProviderOperation operation) {
        this.mOperation = operation;
    }
}
