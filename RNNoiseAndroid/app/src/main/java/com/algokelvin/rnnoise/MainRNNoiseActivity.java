package com.algokelvin.rnnoise;

import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainRNNoiseActivity extends AppCompatActivity {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("denoise");
    }
    public native int rnnoise(String infile,String outfile);

    private final String TAG = "MainRNNoiseActivityLogger";
    private static final int REQUEST_PERMISSION_CODE = 100;
    private boolean isRecording = false;
    private static final int SAMPLE_RATE = 44100; // Hz
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private AudioRecord audioRecord;
    private Button mRecord;
    private Button mPlay;
    private Button mTran;
    private int bufferSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.drawable.back);

        // Make Permissions is work
        if (!checkPermissions()) {
            requestPermissions();
        }

        mRecord = findViewById(R.id.audio_Record);
        mRecord.setOnClickListener(v -> {
            if (isRecording) {
                stopRecord();
            } else {
                startRecord();
            }
        });

        mPlay = findViewById(R.id.audio_play);
        mPlay.setEnabled(false);

        mTran = findViewById(R.id.audio_tran);
        mTran.setEnabled(false);
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            android.Manifest.permission.READ_MEDIA_AUDIO,
                            android.Manifest.permission.RECORD_AUDIO,
                    },
                    REQUEST_PERMISSION_CODE);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.RECORD_AUDIO
                    },
                    REQUEST_PERMISSION_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            if (allPermissionsGranted) {
                Log.i(TAG, "Permission success");
            } else {
                Log.e(TAG, "Permission denied");
            }
        }
    }

    private void startRecord() {
        mRecord.setText("Stop Recording");
        mPlay.setEnabled(false);
        showToast("Recording started...");

        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, bufferSize);
        final byte[] audioData = new byte[bufferSize];
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/recording.wav";

        isRecording = true;
        audioRecord.startRecording();
        new Thread(() -> {
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                Log.i(TAG, "Ready to Recording");
                while (isRecording) {
                    int read = audioRecord.read(audioData, 0, audioData.length);
                    if (read > 0) {
                        outputStream.write(audioData, 0, read);
                    }
                }
                saveAsWav(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void stopRecord() {
        if (isRecording) {
            isRecording = false;
            audioRecord.stop();
            audioRecord.release();
            mRecord.setText("Start Recording");
            mPlay.setEnabled(true);
            mTran.setEnabled(true);
            showToast("Recording stopped");
        }
    }

    private void saveAsWav(String filePath) {
        Log.i(TAG, "filePath: "+filePath);
        File pcmFile = new File(filePath);
        File wavFile = new File(filePath.replace(".wav", "_final.wav"));
        Log.i(TAG, "wavFile: "+wavFile.getAbsolutePath());

        try (FileOutputStream outputStream = new FileOutputStream(wavFile)) {
            // Header WAV
            outputStream.write(wavHeader((int) pcmFile.length(), SAMPLE_RATE, CHANNEL_CONFIG));
            try (FileInputStream inputStream = new FileInputStream(pcmFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            pcmFile.delete(); // Hapus file PCM sementara
            Log.i(TAG, "Success create recording_final.wav");
        } catch (IOException e) {
            Log.e(TAG, "Error: "+e.getMessage());
            e.printStackTrace();
        }
    }

    private byte[] wavHeader(int totalAudioLen, int sampleRate, int channelConfig) {
        int totalDataLen = totalAudioLen + 36;
        int channels = (channelConfig == AudioFormat.CHANNEL_IN_MONO) ? 1 : 2;
        int byteRate = sampleRate * channels * 2;

        return new byte[]{
                // RIFF header
                'R', 'I', 'F', 'F',
                (byte) (totalDataLen & 0xff), (byte) ((totalDataLen >> 8) & 0xff),
                (byte) ((totalDataLen >> 16) & 0xff), (byte) ((totalDataLen >> 24) & 0xff),
                // WAVE header
                'W', 'A', 'V', 'E',
                // fmt subchunk
                'f', 'm', 't', ' ',
                16, 0, 0, 0, // Subchunk1 size
                1, 0, // Audio format (PCM)
                (byte) channels, 0,
                (byte) (sampleRate & 0xff), (byte) ((sampleRate >> 8) & 0xff),
                (byte) ((sampleRate >> 16) & 0xff), (byte) ((sampleRate >> 24) & 0xff),
                (byte) (byteRate & 0xff), (byte) ((byteRate >> 8) & 0xff),
                (byte) ((byteRate >> 16) & 0xff), (byte) ((byteRate >> 24) & 0xff),
                (byte) (channels * 2), 0, // Block align
                16, 0, // Bits per sample
                // data subchunk
                'd', 'a', 't', 'a',
                (byte) (totalAudioLen & 0xff), (byte) ((totalAudioLen >> 8) & 0xff),
                (byte) ((totalAudioLen >> 16) & 0xff), (byte) ((totalAudioLen >> 24) & 0xff)
        };
    }

    public void showToast(String ms) {
        final Toast toast = Toast.makeText(this, ms, Toast.LENGTH_LONG);
        int cnt = 500;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }
}
