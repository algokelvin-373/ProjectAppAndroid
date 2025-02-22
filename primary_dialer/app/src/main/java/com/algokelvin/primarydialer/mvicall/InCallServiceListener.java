package com.algokelvin.primarydialer.mvicall;

import android.telecom.InCallService;

public interface InCallServiceListener {
    void setInCallService(InCallService inCallService);
    void clearInCallService();
}
