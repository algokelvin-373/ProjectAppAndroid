package com.sbi.mvicalllibrary.icallservices;

import android.telecom.InCallService;

public interface InCallServiceListener {
    void setInCallService(InCallService inCallService);
    void clearInCallService();
}
