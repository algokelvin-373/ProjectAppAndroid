package com.sbi.mvicalllibrary.icallservices.audio;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import com.sbi.mvicalllibrary.icallservices.network.NetworkHandler;

public class AudioProccessing {
    private static final int SAMPLE_RATE = 44100; // Sesuaikan dengan kebutuhan
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final int FRAME_SIZE = 256;

    private volatile boolean isProcessing;
    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private Thread processingThread;

    // Deklarasi fungsi native
    private native int processAudioCaptureCall(float[] inputMic, float[] outputSignal);

    @SuppressLint("MissingPermission")
    public void startProcessing() {
        if (isProcessing) return;

        int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);

        // Initialize AudioRecord
        audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.VOICE_COMMUNICATION,
                SAMPLE_RATE,
                CHANNEL_CONFIG,
                AUDIO_FORMAT,
                bufferSize
        );

        // Initialize AudioTrack
        audioTrack = new AudioTrack.Builder()
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build())
                .setAudioFormat(new AudioFormat.Builder()
                        .setEncoding(AUDIO_FORMAT)
                        .setSampleRate(SAMPLE_RATE)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build())
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build();

        isProcessing = true;
        processingThread = new Thread(this::processAudioStream);
        processingThread.start();
    }

    private void processAudioStream() {
        if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED ||
                audioTrack.getState() != AudioTrack.STATE_INITIALIZED) {
            return;
        }

        audioRecord.startRecording();
        audioTrack.play();

        float[] inputBuffer = new float[FRAME_SIZE];
        float[] outputBuffer = new float[FRAME_SIZE];
        byte[] byteBuffer = new byte[FRAME_SIZE * 2];

        while (isProcessing) {
            int bytesRead = audioRecord.read(byteBuffer, 0, byteBuffer.length);
            if (bytesRead > 0) {
                // Konversi byte ke float
                for (int i = 0; i < bytesRead / 2; i++) {
                    inputBuffer[i] = ((byteBuffer[i * 2] & 0xFF) |
                            (byteBuffer[i * 2 + 1] << 8)) / 32768.0f;
                }

                // Panggil JNI untuk memproses audio
                int status = processAudioCaptureCall(inputBuffer, outputBuffer);
                if (status == 0) {
                    // Konversi hasil pemrosesan ke byte
                    for (int i = 0; i < outputBuffer.length; i++) {
                        int sample = (int) (outputBuffer[i] * 32767.0f);
                        byteBuffer[i * 2] = (byte) (sample & 0xFF);
                        byteBuffer[i * 2 + 1] = (byte) ((sample >> 8) & 0xFF);
                    }

                    // Tulis ke AudioTrack
                    audioTrack.write(byteBuffer, 0, byteBuffer.length);
                    Log.i("WRITE AUDIO", "WRITE AUDIO");
                }
            }
        }
    }

    public void stopProcessing() {
        isProcessing = false;

        if (processingThread != null) {
            try {
                processingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
        }

        if (audioTrack != null) {
            audioTrack.stop();
            audioTrack.release();
        }
    }
}

