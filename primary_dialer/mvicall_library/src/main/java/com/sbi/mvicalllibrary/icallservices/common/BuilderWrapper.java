package com.sbi.mvicalllibrary.icallservices.common;

import android.content.ContentProviderOperation.Builder;

/**
 * This class is created for the purpose of compatibility and make the type of
 * ContentProviderOperation available on pre-M SDKs. Since ContentProviderOperation is
 * usually created by Builder and we don’t have access to the type via Builder, so we need to
 * create a wrapper class for Builder first and include type. Then we could use the builder and
 * the type in this class to create a wrapper of ContentProviderOperation.
 */
public class BuilderWrapper {
    private Builder mBuilder;
    private int mType;

    public BuilderWrapper(Builder builder, int type) {
        mBuilder = builder;
        mType = type;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    public void setBuilder(Builder mBuilder) {
        this.mBuilder = mBuilder;
    }
}
