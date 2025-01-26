package com.sbi.mvicalllibrary.icallservices.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.telecom.PhoneAccount;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Compatiblity class for {@link PhoneAccount}
 */
public class PhoneAccountCompat {

    private static final String TAG = PhoneAccountCompat.class.getSimpleName();

    /**
     * Gets the {@link Icon} associated with the given {@link PhoneAccount}
     *
     * @param phoneAccount the PhoneAccount from which to retrieve the Icon
     * @return the Icon, or null
     */
    @Nullable
    public static Icon getIcon(@Nullable PhoneAccount phoneAccount) {
        if (phoneAccount == null) {
            return null;
        }

        if (CompatUtils.isMarshmallowCompatible()) {
            return phoneAccount.getIcon();
        }

        return null;
    }

    /**
     * Builds and returns an icon {@code Drawable} to represent this {@code PhoneAccount} in a user
     * interface.
     *
     * @param phoneAccount the PhoneAccount from which to build the icon.
     * @param context A {@code Context} to use for loading Drawables.
     *
     * @return An icon for this PhoneAccount, or null
     */
    @Nullable
    public static Drawable createIconDrawable(@Nullable PhoneAccount phoneAccount,
                                              @Nullable Context context) {
        if (phoneAccount == null || context == null) {
            return null;
        }

        if (CompatUtils.isMarshmallowCompatible()) {
            return createIconDrawableMarshmallow(phoneAccount, context);
        }

        if (CompatUtils.isLollipopMr1Compatible()) {
            return createIconDrawableLollipopMr1(phoneAccount, context);
        }
        return null;
    }

    @Nullable
    private static Drawable createIconDrawableMarshmallow(PhoneAccount phoneAccount,
                                                          Context context) {
        Icon accountIcon = getIcon(phoneAccount);
        if (accountIcon == null) {
            return null;
        }
        return accountIcon.loadDrawable(context);
    }

    @Nullable
    private static Drawable createIconDrawableLollipopMr1(PhoneAccount phoneAccount,
                                                          Context context) {
        try {
            return (Drawable) PhoneAccount.class.getMethod("createIconDrawable", Context.class)
                    .invoke(phoneAccount, context);
        } catch (ReflectiveOperationException e) {
            return null;
        } catch (Throwable t) {
            Log.e(TAG, "Unexpected exception when attempting to call "
                    + "android.telecom.PhoneAccount#createIconDrawable", t);
            return null;
        }
    }
}
