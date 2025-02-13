package com.algokelvin.recordcallwa.recorder

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors

class App : Application() {
    private val logTag = "RecordWaCall"

    override fun onCreate() {
        super.onCreate()
        //Init settings
        //AppSettings.initIfNeeded(this)
        //Check if installed as Magisk module
        hasCaptureAudioOutputPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAPTURE_AUDIO_OUTPUT) == PackageManager.PERMISSION_GRANTED
        Log.i(logTag, "onCreate() -> hasCaptureAudioOutputPermission: $hasCaptureAudioOutputPermission")
    }

    companion object {
        private var hasCaptureAudioOutputPermission = false
        fun hasCaptureAudioOutputPermission() = hasCaptureAudioOutputPermission
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        Executors.newSingleThreadExecutor().execute {
            //initACRA()
        }
    }


    /*private fun initACRA() {
        try {
            initAcra {
                buildConfigClass = BuildConfig::class.java
                reportFormat = StringFormat.KEY_VALUE_LIST
                reportContent = listOf(
                    ReportField.USER_COMMENT,
                    ReportField.TOTAL_MEM_SIZE,
                    ReportField.AVAILABLE_MEM_SIZE,
                    ReportField.PACKAGE_NAME,
                    ReportField.APP_VERSION_NAME,
                    ReportField.APP_VERSION_CODE,
                    ReportField.ANDROID_VERSION,
                    ReportField.BRAND,
                    ReportField.PHONE_MODEL,
                    ReportField.PRODUCT,
                    ReportField.USER_APP_START_DATE,
                    ReportField.USER_CRASH_DATE,
                    ReportField.THREAD_DETAILS,
                    ReportField.STACK_TRACE,
                    ReportField.LOGCAT
                )

                mailSender {
                    enabled = true
                    mailTo = Constants.contactEmail
                    reportAsFile = false
                    //setReportFileName("crash_log.txt")
                }

                notification {
                    enabled = true
                    title = getString(R.string.crash_notif_title)
                    text = getString(R.string.crash_dialog_text)
                    channelName = getString(R.string.app_crash_notification_channel)
                    sendButtonText = getString(R.string.send)
                    discardButtonText = getString(R.string.cancel)
                    sendOnClick = true

                    resDiscardButtonIcon = R.drawable.crash_log_discard
                    resSendButtonIcon = R.drawable.crash_log_send
                }

                //Notification may not work, also use dialog for now. See https://github.com/ACRA/acra/issues/1146
                dialog {
                    title = getString(R.string.crash_notif_title)
                    text = getString(R.string.crash_dialog_text)

                }
            }


        } catch (e: Exception) {
            //Already called. Ignore. It seems to be called more than once on rare occasions
            e.printStackTrace()
        }
    }*/
}