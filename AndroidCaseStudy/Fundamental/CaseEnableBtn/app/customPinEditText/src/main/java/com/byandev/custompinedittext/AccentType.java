package com.byandev.custompinedittext;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef(flag=true, value={InputPinCustom.ACCENT_NONE, InputPinCustom.ACCENT_ALL, InputPinCustom.ACCENT_CHARACTER})
@Retention(RetentionPolicy.SOURCE)
public @interface AccentType {}
