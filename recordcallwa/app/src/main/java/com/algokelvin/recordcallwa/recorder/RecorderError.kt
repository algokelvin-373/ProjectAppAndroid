package com.algokelvin.recordcallwa.recorder

enum class RecorderError {
    AudioRecordInUse,
    MediaCodecQueueInputBufferFailed,
    AudioRecordReadFailed,
    EmptyInputBuffer,
    MediaMuxerWriteFailed,
    MediaRecorderError,
    MediaCodecException,
    RemoteError
}