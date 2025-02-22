package com.algokelvin.recordcallwa.recorder

import android.os.Build
import android.util.Log

enum class Encoder(val id: Int) {
    MediaCodec(1), AndroidMediaRecorder(2);

    override fun toString(): String {
        return when (this) {
            AndroidMediaRecorder -> "AndroidMediaRecorder"
            MediaCodec -> "MediaCodecRecorder"
        }
    }

    companion object {
        private const val logTag = "RecordWaCall"
        private val map = values().associateBy(Encoder::id)
        fun fromIdOrDefault(id: Int): Encoder {
            return when (id) {
                0 -> {
                    if (forceAndroidMediaRecorder()) {
                        Log.i(logTag, "fromIdOrDefault -> forceAndroidMediaRecorder() is true. Returning AndroidMediaRecorder")

                        AndroidMediaRecorder
                    } else {
                        getDefaultEncoder()
                    }
                }
                else -> {
                    val encoder = map[id] ?: getDefaultEncoder()
                    Log.i(logTag, "fromIdOrDefault -> User changed -> Returning $encoder")
                    encoder
                }
            }
        }

        private fun forceAndroidMediaRecorder() = isLGE() || (isOnePlus() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)

        private fun isLGE() = Build.MANUFACTURER.equals("LGE", ignoreCase = true)

        private fun isOnePlus() = Build.MANUFACTURER.equals("ONEPLUS", ignoreCase = true)

        private fun getDefaultEncoder() = AndroidMediaRecorder.also {
            Log.i(logTag, "getDefaultEncoder -> Android 10 and above. Returning AndroidMediaRecorder")
        }
    }
}