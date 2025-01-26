package com.sbi.mvicalllibrary.icallservices;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telecom.PhoneAccount;
import android.telecom.TelecomManager;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import com.sbi.mvicalllibrary.R;

import java.util.Arrays;

public class CallerInfoUtils {

    public CallerInfoUtils() {
    }

    private static final int QUERY_TOKEN = -1;

    public static CallerInfo getCallerInfoForCall(Context context, Call call, CallerInfoAsyncQuery.OnQueryCompleteListener listener) {
        CallerInfo info = buildCallerInfo(context, call);


        if (info.numberPresentation == TelecomManager.PRESENTATION_ALLOWED) {
            CallerInfoAsyncQuery.startQuery(QUERY_TOKEN, context, info, listener, call);
        }
        return info;
    }

    public static CallerInfo buildCallerInfo(Context context, Call call) {
        CallerInfo info = new CallerInfo();

        info.cnapName = call.getCnapName();
        info.name = info.cnapName;
        info.numberPresentation = call.getNumberPresentation();
        info.namePresentation = call.getCnapNamePresentation();
        info.callSubject = call.getCallSubject();

        String number = call.getNumber();
        if (!TextUtils.isEmpty(number)) {
            final String[] numbers = number.split("&");
            number = numbers[0];
            if (numbers.length > 1) {
                info.forwardingNumber = numbers[1];
            }

            number = modifyForSpecialCnapCases(context, info, number, info.numberPresentation);
            info.phoneNumber = number;
        }

        if ((call.getHandle() != null && PhoneAccount.SCHEME_VOICEMAIL.equals(call.getHandle().getScheme())) || isVoiceMailNumber(context, call)) {
            info.markAsVoiceMail(context);
        }

        ContactInfoCache.getInstance(context).maybeInsertCnapInformationIntoCache(context, call, info);

        return info;
    }

    public static boolean isVoiceMailNumber(Context context, Call call) {
        TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return telecomManager.isVoiceMailNumber(call.getTelecommCall().getDetails().getAccountHandle(), call.getNumber());
    }


    static String modifyForSpecialCnapCases(Context context, CallerInfo ci, String number, int presentation) {
        if (ci == null || number == null) return number;

        final String[] absentNumberValues = context.getResources().getStringArray(R.array.absent_num);
        if (Arrays.asList(absentNumberValues).contains(number) && presentation == TelecomManager.PRESENTATION_ALLOWED) {
            number = context.getString(R.string.unknown);
            ci.numberPresentation = TelecomManager.PRESENTATION_UNKNOWN;
        }

        if (ci.numberPresentation == TelecomManager.PRESENTATION_ALLOWED || (ci.numberPresentation != presentation && presentation == TelecomManager.PRESENTATION_ALLOWED)) {
            if (isCnapSpecialCaseRestricted(number)) {
                number = context.getString(R.string.private_num);
                ci.numberPresentation = TelecomManager.PRESENTATION_RESTRICTED;
            } else if (isCnapSpecialCaseUnknown(number)) {
                number = context.getString(R.string.unknown);
                ci.numberPresentation = TelecomManager.PRESENTATION_UNKNOWN;
            }
        }
        return number;
    }

    private static boolean isCnapSpecialCaseRestricted(String n) {
        return n.equals("PRIVATE") || n.equals("P") || n.equals("RES");
    }

    private static boolean isCnapSpecialCaseUnknown(String n) {
        return n.equals("UNAVAILABLE") || n.equals("UNKNOWN") || n.equals("UNA") || n.equals("U");
    }

    public static void sendViewNotification(Context context, Uri contactUri) {
//        final ContactLoader loader = new ContactLoader(context, contactUri, true /* postViewNotification */);
//        loader.registerListener(0, new OnLoadCompleteListener<Contact>() {
//            @Override
//            public void onLoadComplete(Loader<Contact> loader, Contact contact) {
//                try {
//                    loader.reset();
//                } catch (RuntimeException e) {
//                }
//            }
//        });
//        loader.startLoading();
    }
}
