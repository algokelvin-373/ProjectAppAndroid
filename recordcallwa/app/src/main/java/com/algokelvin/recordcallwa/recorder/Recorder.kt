package com.algokelvin.recordcallwa.recorder

import android.content.Context

interface Recorder {
    /**
     * So that we can deputising if recording will be silent.
     */
    fun isSIPRecorder(): Boolean
    fun isHelperRecorder(): Boolean
    fun startRecording()
    fun stopRecording()
    fun pauseRecording()
    fun resumeRecording()
    fun getState(): ServerRecordingState
    fun getConfig(): RecorderConfig
    fun needsPermissions(context: Context): Array<String>
    fun getRoughRecordingTimeInMillis(): Long
}