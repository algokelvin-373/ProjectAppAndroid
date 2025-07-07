package com.algokelvin.primarydialer.mvicall.interfaces;

import android.graphics.Bitmap;

public interface PhoneNumberService {
    void getPhoneNumberInfo(String phoneNumber, NumberLookupListener listener, ImageLookupListener imageListener, boolean isIncoming);

    interface NumberLookupListener {
        void onPhoneNumberInfoComplete(PhoneNumberInfo info);
    }

    interface ImageLookupListener {
        void onImageFetchComplete(Bitmap bitmap);
    }

    interface PhoneNumberInfo {
        String getDisplayName();
        String getNumber();
        int getPhoneType();
        String getPhoneLabel();
        String getNormalizedNumber();
        String getImageUrl();
        boolean isBusiness();
    }
}
