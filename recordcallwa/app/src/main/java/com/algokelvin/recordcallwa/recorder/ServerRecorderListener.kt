package com.algokelvin.recordcallwa.recorder

interface ServerRecorderListener {
    fun onRecordingStateChange(newState: ServerRecordingState)
}