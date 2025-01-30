package com.algokelvin.audiojernih;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;
import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.AudioTrackSink;
import org.webrtc.MediaConstraints;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.audio.AudioDeviceModule;
import org.webrtc.audio.JavaAudioDeviceModule;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {
    private PeerConnectionFactory factory;
    private AudioSource audioSource;
    private Interpreter tflite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inisialisasi WebRTC - Work
        PeerConnectionFactory.initialize(
                PeerConnectionFactory.InitializationOptions
                        .builder(this)
                        .createInitializationOptions()
        );

        // 2. Setup Audio Device dengan Noise Suppression
        AudioDeviceModule audioDevice = JavaAudioDeviceModule.builder(this)
                .setUseHardwareNoiseSuppressor(false) // Nonaktifkan hardware NS
                .createAudioDeviceModule();

        // 3. Buat PeerConnectionFactory
        factory = PeerConnectionFactory.builder()
                .setAudioDeviceModule(audioDevice)
                .createPeerConnectionFactory();

        // 4. Load Model RNNoise
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            Log.e("MainActivity", "Error loading model", e);
            finish();
            return;
        }

        // 5. Mulai Panggilan
        startCall();
    }

    // Tambahkan method untuk load model
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = getAssets().openFd("rnnoise.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private void startCall() {
        // 1. Buat Audio Source dengan constraints noise suppression
        MediaConstraints constraints = new MediaConstraints();
        constraints.mandatory.add(new MediaConstraints.KeyValuePair("googNoiseSuppression", "true"));

        audioSource = factory.createAudioSource(constraints); // Set constraints di sini
        AudioTrack audioTrack = factory.createAudioTrack("audioTrack", audioSource);

        audioTrack.addSink(new AudioTrackSink() {
            @Override
            public void onData(ByteBuffer byteBuffer, int i, int i1, int i2, int i3, long l) {
                // Konversi ByteBuffer ke byte[]
                byte[] audioData = new byte[byteBuffer.remaining()];
                byteBuffer.get(audioData);

                // Proses audio di thread terpisah
                new Thread(() -> {
                    float[] processedAudio = processWithRNNoise(audioData);
                    sendToNetwork(processedAudio);
                }).start();
            }
        });
    }

    /*private void enableWebRtcNoiseSuppression() {
        // Aktifkan NS bawaan WebRTC
        MediaConstraints constraints = new MediaConstraints();
        constraints.mandatory.add(new MediaConstraints.KeyValuePair("googNoiseSuppression", "true"));

        audioSource.setConstraints(constraints);
    }*/

    private float[] processWithRNNoise(byte[] input) {
        // Konversi byte[] ke float[]
        float[] floatInput = new float[input.length / 2];
        for (int i = 0; i < floatInput.length; i++) {
            short sample = (short) ((input[2*i] & 0xFF) | (input[2*i+1] << 8));
            floatInput[i] = sample / 32768.0f;
        }

        // Proses dengan TFLite
        float[][] output = new float[1][floatInput.length];
        tflite.run(floatInput, output);
        return output[0];
    }

    private void sendToNetwork(float[] audio) {
        // Implementasi pengiriman via WebRTC DataChannel
        // Contoh: https://webrtc.github.io/webrtc-org/native-code/android/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tflite != null) {
            tflite.close();
        }
    }
}