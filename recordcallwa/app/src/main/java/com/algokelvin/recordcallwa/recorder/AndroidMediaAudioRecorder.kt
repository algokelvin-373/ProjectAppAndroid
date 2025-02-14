package com.algokelvin.recordcallwa.recorder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class AndroidMediaAudioRecorder(private val recorderConfig: RecorderConfig) : Recorder, MediaRecorder.OnErrorListener, MediaRecorder.OnInfoListener {
    private val logTag = "AndroidMediaAudioRecorder"
    private var timeAtPause: Long = 0
    private var elapsedTimeOnResumeInMicroSeconds: Long = 0
    private var recordingStartTime: Long = 0
    private var recorder: MediaRecorder? = null
    override fun isSIPRecorder() = false
    override fun isHelperRecorder() = false

    private var serverRecordingState: ServerRecordingState by Delegates.observable<ServerRecordingState>(ServerRecordingState.Stopped) { _, oldValue, newValue ->
        Log.i(logTag, "State value updated, oldValue: $oldValue, newValue: $newValue")
        if (oldValue != newValue) {
            Log.i(logTag, "Since oldValue != newValue calling recorderListener.onRecordingState ")
            recorderConfig.serverRecorderListener.onRecordingStateChange(newValue)
        }
    }

    override fun needsPermissions(context: Context): Array<String> {
        val permissionsNeeded = arrayOf(Manifest.permission.RECORD_AUDIO).filter { permission ->
            ContextCompat.checkSelfPermission(context.applicationContext, permission) == PackageManager.PERMISSION_DENIED
        }.toTypedArray()

        Log.i(logTag, "needsPermissions -> permissions: ${permissionsNeeded.joinToString(", ")}")
        return permissionsNeeded
    }


    override fun getRoughRecordingTimeInMillis(): Long {
        val roughRecordingTimeInMillis = TimeUnit.NANOSECONDS.toMillis((System.nanoTime() - recordingStartTime) - TimeUnit.MICROSECONDS.toNanos(elapsedTimeOnResumeInMicroSeconds))
        Log.i(logTag, "roughRecordingTimeInMillis: $roughRecordingTimeInMillis")
        return roughRecordingTimeInMillis

    }

    private fun setElapsedTimeOnResume() {
        val changeInMicroSeconds = (System.nanoTime() / 1000L - timeAtPause)
        elapsedTimeOnResumeInMicroSeconds += changeInMicroSeconds

        Log.i(logTag, "setElapsedTimeOnResume() -> elapsedTimeOnResume: $elapsedTimeOnResumeInMicroSeconds")
    }


    override fun startRecording() {
        Log.i(logTag, "start() -> Start called. AudioRecorderConfig is: $recorderConfig")

        recorder = MediaRecorder().apply {
            try {
                setAudioChannels(2) // 2
                setAudioEncodingBitRate(64000) // 64000
                setAudioSamplingRate(44100) // 44100
                setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION) // 6
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) // 2
                setOutputFile(FileOutputStream(recorderConfig.recordingFile).fd)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC) // 3
                setOnErrorListener(this@AndroidMediaAudioRecorder)
                setOnInfoListener(this@AndroidMediaAudioRecorder)

                prepare()
                start()
                elapsedTimeOnResumeInMicroSeconds = 0
                timeAtPause = 0
                recordingStartTime = System.nanoTime()
                serverRecordingState = ServerRecordingState.Recording
                Log.i(logTag, "startRecording() -> Recording started")
            } catch (e: Exception) {
                serverRecordingState = ServerRecordingState.Error(RecorderError.MediaRecorderError, e)
                Log.i(logTag, "startRecording() -> Recording cannot start! Error is:")
            }
        }
    }

    override fun stopRecording() {
        Log.i(logTag, "stopRecording() -> Stop called")
        recorder?.apply {
            try {
                stop()
                release()
                recorder = null
                serverRecordingState = ServerRecordingState.Stopped

            } catch (e: Exception) {
                Log.i(logTag, "stopRecording() -> Exception while stopping")
                recorder = null
                serverRecordingState = ServerRecordingState.Error(RecorderError.MediaRecorderError, e)
            }
        }

    }

    private fun setTimeAtPause() {
        timeAtPause = System.nanoTime() / 1000L
    }

    override fun pauseRecording() {
        Log.i(logTag, "pauseRecording() -> Pause called")
        try {
            if (serverRecordingState == ServerRecordingState.Recording) {
                Log.i(logTag, "pauseRecording() -> recordingState == RecordingState.Recording. Pausing...")
                setTimeAtPause()
                recorder?.pause()
                serverRecordingState = ServerRecordingState.Paused
            } else {
                Log.i(logTag, "pauseRecording() -> Error! Pause should only be called after Start or before Stop is called. Current state is: $serverRecordingState")
            }
        } catch (e: Exception) {
            Log.i(logTag, "pauseRecording() -> Error! Pause called either before start or after stop. Current state is: $serverRecordingState")
        }
    }

    override fun resumeRecording() {
        Log.i(logTag, "resumeRecording() -> Resume called")
        try {
            if (serverRecordingState == ServerRecordingState.Paused) {
                Log.i(logTag, "resumeRecording() -> recordingState == RecordingState.Paused. Resuming...")
                setElapsedTimeOnResume()
                recorder?.resume()
                serverRecordingState = ServerRecordingState.Recording

            } else {
                Log.i(logTag, "resumeRecording() -> Error! Resume should only be called after Start or before Stop is called. Current state is: $serverRecordingState")
            }
        } catch (e: Exception) {
            Log.i(logTag, "resumeRecording() -> Error! Resume called either before start or after stop. Current state is: $serverRecordingState")
        }
    }

    override fun getState(): ServerRecordingState {
        return serverRecordingState
    }

    override fun getConfig() = recorderConfig

    override fun onError(mr: MediaRecorder, what: Int, extra: Int) {
        Log.i(logTag, "onError() -> what: $what, extra: $extra")
        when (what) {
            MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN -> {
                Log.i(logTag, "onError() -> MEDIA_RECORDER_ERROR_UNKNOWN")
            }
            MediaRecorder.MEDIA_ERROR_SERVER_DIED -> {
                Log.i(logTag, "onError() -> MEDIA_ERROR_SERVER_DIED")
            }
            else -> {
                Log.i(logTag, "onError() -> Unknown error")
            }
        }

        serverRecordingState = ServerRecordingState.Error(RecorderError.MediaRecorderError, Exception("Error code: $what, Error extra: $extra"))
    }

    override fun onInfo(mr: MediaRecorder, what: Int, extra: Int) {
        Log.i(logTag, "onInfo() -> what: $what, extra: $extra")

        when (what) {
            MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN -> {
                Log.i(logTag, "onInfo() -> MEDIA_RECORDER_INFO_UNKNOWN")
            }
            MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED -> {
                Log.i(logTag, "onInfo() -> MEDIA_RECORDER_INFO_MAX_DURATION_REACHED")
            }
            MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED -> {
                Log.i(logTag, "onInfo() -> MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED")
            }
            else -> {
                Log.i(logTag, "onInfo() -> Unknown info")
            }
        }
    }

    override fun toString(): String {
        return "AndroidMediaAudioRecorder(recorderConfig=$recorderConfig)"
    }

}