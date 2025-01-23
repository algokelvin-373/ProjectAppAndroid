package com.algokelvin.rnnoise;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MainReferenceActivity extends AppCompatActivity {
    private final String TAG = "MainActivityLogger";
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String outputFilePath;
    private boolean isRecording = false;
    private boolean mIsPlaying = false;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("denoise");
    }
    public native int rnnoise(String infile,String outfile);


    private final int REQ_PERMISSION_AUDIO = 0x01;
    private Button mRecord, mPlay,mTran,mPlay2,mRealtimeTran,mRealtimeTran_Flag;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/pauseRecordDemo/";
    private File mAudioFile = new File(path+"before.pcm");
    private File mAudioFile2 = new File(path+"after.pcm");

    //used to real time tran.
    private File mAudioBuffer_before1 = new File(path+"bb_1.pcm");
    private File mAudioBuffer_after1 = new File(path+"ba_1.pcm");

    private LinkedList<short[]> m_in_q; //队列
    private LinkedList<short[]> m_in_q2; //队列2


    private Handler handler=null;
    private Thread mCaptureThread = null;
    private boolean mIsRecording, mIsRealTimeTraning;

    private static final int SAMPLE_RATE = 44100; // Hz
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private AudioRecord audioRecord;
    private int bufferSize;

    private boolean rt_flag = false;

    //private PlayTask mPlay_task;
    //private RecordTask mRecord_task;
    private TranTask mTran_task;
    //private RealTimeRecordTask mRealTimeRecord_Task;
    private RealTimeTranTask mRealTimeTran_task; //一个类
    //private RealTimePlayTask mRealTimePlay_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.drawable.back);

        handler=new Handler();
        m_in_q = new LinkedList<short[]>();
        m_in_q2 = new LinkedList<short[]>();

        mRecord = findViewById(R.id.audio_Record);
        mRecord.setOnClickListener(v -> {
            if (isRecording) {
                //stopAudioRecord();
                stopAudioNewMethod();
            } else {
                startAudioNewMethod();
                //startAudioRecord();
            }
        });

        mPlay = findViewById(R.id.audio_play);
        mPlay.setEnabled(false);
        mPlay.setOnClickListener(v -> {
            if (mPlay.getTag() == null) {
                startAudioPlay(mAudioFile);
            } else {
                stopAudioPlay(mAudioFile);
            }
        });

        mTran = findViewById(R.id.audio_tran);
        mTran.setEnabled(false);
        mTran.setOnClickListener(v -> {
            if (mTran.getTag() == null) {
                startAudioTran();
            }
        });

        mPlay2 = findViewById(R.id.audio_play2);
        mPlay2.setEnabled(false);
        mPlay2.setOnClickListener(v -> {
            if (mPlay2.getTag() == null) {
                startAudioPlay2(mAudioFile2);
            } else {
                stopAudioPlay2(mAudioFile2);
            }
        });


        mRealtimeTran = findViewById(R.id.rt_audio_play);
        mRealtimeTran.setOnClickListener(v -> {
            if (mRealtimeTran.getTag() == null) {
                startRealTimeAudioTran();
            } else {
                stopRealTimeTran();
            }
        });

        mRealtimeTran_Flag = findViewById(R.id.rt_audio_tran_flag);
        mRealtimeTran_Flag.setOnClickListener(v -> {
            if (rt_flag) {
                rt_flag=false;
                mRealtimeTran_Flag.setText("开启实时降噪");
            }
            else {
                rt_flag=true;
                mRealtimeTran_Flag.setText("关闭实时降噪");
            }
        });
    }

    private void startAudioNewMethod() {
        mRecord.setText("Stop Recording");
        mPlay.setEnabled(false);
        showToast("Recording started...");

        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, bufferSize);
        final byte[] audioData = new byte[bufferSize];
        //String filePath = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "recording.wav").getAbsolutePath();
        // Simpan di folder /storage/emulated/0/Music
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

    private void stopAudioNewMethod() {
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
        } catch (IOException e) {
            Log.i(TAG, "Error: "+e.getMessage());
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

    private void startAudioRecord() {
        if (checkPermission()) {
            // Create directory to save audio files
            File audioDir = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "pauseRecordDemo");
            if (!audioDir.exists()) {
                audioDir.mkdirs();
            }

            // Set output file path
            outputFilePath = new File(audioDir, "audio_record.3gp").getAbsolutePath();

            // Initialize MediaRecorder
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(outputFilePath);

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                isRecording = true;
                mRecord.setText("Stop Recording");
                mPlay.setEnabled(false);
                showToast("Recording started...");
            } catch (IOException e) {
                Log.e(TAG, "MediaRecorder prepare failed: " + e.getMessage());
                showToast("Failed to start recording");
            }
        } else {
            requestPermission();
        }
    }

    private void stopAudioRecord() {
        Log.i(TAG, "MainActivity - stopAudioRecord");
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            mRecord.setText("Start Recording");
            mPlay.setEnabled(true);
            mTran.setEnabled(true);
            showToast("Recording stopped. File saved at: " + outputFilePath);
            Log.i(TAG, "File saved at: " + outputFilePath);
        }
    }

    private void stopRealTimeTran() {
        Log.i(TAG, "MainActivity - stopRealTimeTran");
        mIsRealTimeTraning = false;
        mRealtimeTran.setTag(null);
        mRealtimeTran.setText("Umpan balik waktu nyata");
        mRealtimeTran.setEnabled(true);
    }

    private void startAudioPlay(File A) {
        Log.i(TAG, "MainActivity - startAudioPlay");
        mPlay.setTag(this);
        mPlay.setText("Berhenti");

        if (outputFilePath != null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(outputFilePath);
                mediaPlayer.prepare();
                mediaPlayer.start();
                showToast("Mulai memutar audio yang direkam.");
            } catch (IOException e) {
                Log.e("AUDIO_PLAY", "MediaPlayer prepare failed: " + e.getMessage());
                showToast("Failed to play audio");
            }
        } else {
            showToast("No audio file found to play");
        }
    }

    private void startAudioPlay2(File A) {
        /*Log.i(TAG, "MainActivity - startAudioPlay2");
        mPlay2.setTag(this);
        mPlay2.setText("berhenti");

        mPlay_task = new PlayTask(A);
        mPlay_task.execute();*/

        showToast("Mulai memutar audio yang diproses");
    }

    private void startAudioTran() {
        Log.i(TAG, "MainActivity - startAudioTran");
        mTran.setTag(this);
        mTran.setText("Mengonversi");
        mTran.setEnabled(false);

        mTran_task= new TranTask();
        mTran_task.execute();

        showToast("Pengurangan kebisingan dimulai");
    }

    private void startRealTimeAudioTran() {
        /*Log.i(TAG, "MainActivity - startRealTimeAudioTran");
        mRealtimeTran.setTag(this);
        mRealtimeTran.setText("Hentikan umpan balik waktu nyata");
        //三个线程
        mRealTimeRecord_Task=new RealTimeRecordTask();
        mRealTimeTran_task= new RealTimeTranTask();
        mRealTimePlay_task= new RealTimePlayTask();
        mRealTimeRecord_Task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        mRealTimeTran_task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        mRealTimePlay_task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/

        showToast("Umpan balik waktu nyata dimulai");
    }

    private void stopAudioPlay(File A) {
        Log.i(TAG, "MainActivity - stopAudioPlay");
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                showToast("Audio stopped");
                mIsPlaying = false;
                mPlay.setTag(null);
                mPlay.setText("Putar audio yang direkam");
            }
            mediaPlayer.release();
            mediaPlayer = null;
        } else {
            showToast("No audio is playing");
        }
    }

    private void stopAudioPlay2(File A) {
        Log.i(TAG, "MainActivity - stopAudioPlay2");
        mIsPlaying = false;
        mPlay2.setTag(null);
        mPlay2.setText("Putar audio yang telah diproses");
    }


    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Untuk Android 13+ (API 33+), gunakan izin granular
            int readImages = ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.READ_MEDIA_IMAGES);
            int readAudio = ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.READ_MEDIA_AUDIO);
            int readVideo = ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.READ_MEDIA_VIDEO);
            return readImages == PackageManager.PERMISSION_GRANTED &&
                    readAudio == PackageManager.PERMISSION_GRANTED &&
                    readVideo == PackageManager.PERMISSION_GRANTED;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Untuk Android 10 hingga 12, gunakan izin scoped storage
            int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.RECORD_AUDIO);
            return result == PackageManager.PERMISSION_GRANTED &&
                    result1 == PackageManager.PERMISSION_GRANTED;
        } else {
            // Untuk Android 9 ke bawah
            int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.RECORD_AUDIO);
            return result == PackageManager.PERMISSION_GRANTED &&
                    result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, REQ_PERMISSION_AUDIO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_AUDIO:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        showToast("Permission Granted");
                    } else {
                        showToast("Permission  Denied");
                    }
                }
                break;
        }
    }

    public void showToast(String ms) {
        final Toast toast = Toast.makeText(this, ms, Toast.LENGTH_LONG);
        int cnt=500;
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

    public static void shortToByte_LH(short shortVal, byte[] b) {
        b[0] = (byte) (shortVal & 0xff);
        b[1] = (byte) (shortVal >> 8 & 0xff);
    }

    public static short byteToShort_HL(byte[] b) {
        short result;
        result = (short)((((b[0]) << 8) & 0xff00 | b[1] & 0x00ff));
        return result;
    }


    /*class RecordTask extends AsyncTask<Void,Integer,Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            mIsRecording = true;
            try {
                // 开通输出流到指定的文件
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(mAudioFile)));
                // 根据定义好的几个配置，来获取合适的缓冲大小
                int bufferSize = AudioRecord.getMinBufferSize(mFrequence,
                        mChannelConfig, mAudioEncoding);

                // 实例化AudioRecord
                AudioRecord Record = new AudioRecord(
                        MediaRecorder.AudioSource.MIC, mFrequence,
                        mChannelConfig, mAudioEncoding, bufferSize);
                // 定义缓冲
                short[] buffer = new short[bufferSize];


                // 开始录制
                Record.startRecording();


                int r = 0; // 存储录制进度
                byte[] b=new byte[2];
                // 定义循环，根据isRecording的值来判断是否继续录制
                while (mIsRecording) {
                    // 从bufferSize中读取字节，返回读取的short个数
                    int bufferReadResult = Record
                            .read(buffer, 0, buffer.length);
                    // 循环将buffer中的音频数据写入到OutputStream中
                    for (int i = 0; i < bufferReadResult; i++) {
                        shortToByte_LH(buffer[i], b);

                        dos.writeShort(byteToShort_HL(b));
                    }
                    publishProgress(new Integer(r)); // 向UI线程报告当前进度
                    r++; // 自增进度值
                }
                // 录制结束
                Record.stop();
                Log.i("slack", "::" + mAudioFile.length());
                dos.close();
            } catch (Exception e) {
                // TODO: handle exception
                Log.e("slack", "::" + e.getMessage());
            }
            return null;
        }


        // 当在上面方法中调用publishProgress时，该方法触发,该方法在UI线程中被执行
        protected void onProgressUpdate(Integer... progress) {
            //
        }


        protected void onPostExecute(Void result) {

        }

    }*/
    /*class PlayTask extends AsyncTask<Void,Void,Void> {
        File AudioFile;

        PlayTask(File A) {
            AudioFile = A;
        } //初始化

        @Override

        protected Void doInBackground(Void... arg0) {
            mIsPlaying = true;
            int bufferSize = AudioRecord.getMinBufferSize(mFrequence,
                    mChannelConfig, mAudioEncoding);
            Log.v("noisemain", "currentX=" + bufferSize);
            short[] buffer = new short[bufferSize];
            try {
                // 定义输入流，将音频写入到AudioTrack类中，实现播放
                DataInputStream dis = new DataInputStream(
                        new BufferedInputStream(new FileInputStream(AudioFile)));
                // 实例AudioTrack
                AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC,
                        mFrequence,
                        mPlayChannelConfig, mAudioEncoding, bufferSize,
                        AudioTrack.MODE_STREAM);
                // 开始播放
                track.play();
                byte [] b=new byte[2];
                // 由于AudioTrack播放的是流，所以，我们需要一边播放一边读取
                while (mIsPlaying && dis.available() > 0) {
                    int i = 0;
                    while (dis.available() > 0 && i < buffer.length) {
                        //buffer[i] = dis.readShort();
                        shortToByte_LH(dis.readShort(),b);
                        buffer[i] =byteToShort_HL(b);
                        i++;
                    }
                    // 然后将数据写入到AudioTrack中
                    track.write(buffer, 0, buffer.length);
                }


                // 播放结束
                track.stop();
                dis.close();
                mIsPlaying = false;
                mPlay.setTag(null);
                mPlay.setText("播放录音音频");
                mPlay2.setTag(null);
                mPlay2.setText("播放处理音频");
            } catch (Exception e) {
                // TODO: handle exception
                Log.e("slack", "error:" + e.getMessage());
            }
            return null;
        }


        protected void onPostExecute(Void result) {

        }


        protected void onPreExecute() {

        }
    }*/
    class TranTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                new Thread(){
                    public void run(){
                        rnnoise(Environment.getExternalStorageDirectory()
                                .getAbsolutePath() + "/pauseRecordDemo/"+mAudioFile.getName(),Environment.getExternalStorageDirectory()
                                .getAbsolutePath() + "/pauseRecordDemo/"+mAudioFile2.getName());
                        handler.post(runnableUi);
                    }
                }.start();



            } catch (Exception e) {
                // TODO: handle exception
                Log.e("slack", "error:" + e.getMessage());
            }
            return null;
        }


        Runnable   runnableUi=new  Runnable(){
            @Override
            public void run() {
                //更新界面
                mTran.setTag(null);
                mTran.setEnabled(true);
                mTran.setText("降噪处理");
                mPlay2.setEnabled(true);
                showToast("降噪完毕");
            }

        };
        protected void onPostExecute(Void result) {

        }


        protected void onPreExecute() {

        }
    }

    /*class RealTimeRecordTask extends AsyncTask<Void,Integer,Void> {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected Void doInBackground(Void... arg0) {
            mIsRealTimeTraning=true;
            try {

                // 实例化AudioRecord
                AudioRecord Record = new AudioRecord(
                        MediaRecorder.AudioSource.MIC, mFrequence,
                        mChannelConfig, mAudioEncoding, bufferSize);
                // 定义缓冲
                short[] buffer = new short[bufferSize];
                short[] tmpBuf = new short[bufferSize];
                // 开始录制
                Record.startRecording();
                int r = 0; // 存储录制进度
                byte[] b=new byte[2];
                int shortnum=0;
                // 定义循环，根据isRecording的值来判断是否继续录制
                while (mIsRealTimeTraning) {
                    // 从bufferSize中读取字节，返回读取的short个数
                    int bufferReadResult = Record
                            .read(buffer, 0, buffer.length,AudioRecord.READ_BLOCKING);
                    // 大小端变换


                    for (int i = 0; i < bufferReadResult; i++) {
                        shortToByte_LH(buffer[i], b);

                        tmpBuf[i]=(byteToShort_HL(b));

                    }
                    shortnum+=bufferReadResult;



                    m_in_q.add(tmpBuf);

                    // Log.i("rec_mq","ok");
                }
                // 录制结束
                Record.stop();
            } catch (Exception e) {
                // TODO: handle exception
                Log.e("slack", "::" + e.getMessage());
            }
            return null;
        }


        // 当在上面方法中调用publishProgress时，该方法触发,该方法在UI线程中被执行
        protected void onProgressUpdate(Integer... progress) {
            //
        }


        protected void onPostExecute(Void result) {

        }
    }*/

    class RealTimeTranTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                // 定义缓冲
                short[] outBuf = new short[bufferSize+480];
                short[] tmpBuf = new short[bufferSize];
                short []lastframe =new short[480];
                for (int i=0;i<480;i++){ lastframe[i]=0;}


                mIsRealTimeTraning=true;
                Log.i("retran_mq","ok");
                while (mIsRealTimeTraning) {
                    try {
                        tmpBuf = m_in_q.getFirst();
                        m_in_q.removeFirst();
                    }
                    catch (Exception e){
                        continue;
                    }

                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(mAudioBuffer_before1)));
                    for (int i=0;i<480;i++){ dos.writeShort(lastframe[i]);}
                    for (int i = 0; i < tmpBuf.length; i++) {
                        dos.writeShort(tmpBuf[i]);
                    }

                    dos.close();
                    for (int i=0;i<480;i++){ lastframe[i]=tmpBuf[bufferSize-480+i];}

                    rnnoise(Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + "/pauseRecordDemo/"+mAudioBuffer_before1.getName(),Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + "/pauseRecordDemo/"+mAudioBuffer_after1.getName());

                    File a;
                    if (rt_flag){a=mAudioBuffer_after1;}
                    else{a=mAudioBuffer_before1;}
                    DataInputStream dis= new DataInputStream(new BufferedInputStream(new FileInputStream(a)));
                    int i=0;
                    while (dis.available() > 0 && i < bufferSize) {
                        outBuf[i] =dis.readShort();
                        i++;
                    }
                    dis.close();


                    //outBuf = tmpBuf.clone();
                    m_in_q2.add(outBuf);//处理结果放入对列2
                    //m_in_q.removeFirst();


                }
            } catch (Exception e) {
                // TODO: handle exception
                Log.e("slack", "error:" + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {

        }


        protected void onPreExecute() {

        }
    }

    /*class RealTimePlayTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            mIsPlaying = true;
            //Log.i("play_mq","createok");
            short[] buffer = new short[bufferSize];
            short[] tmpBuf = new short[bufferSize];
            try {

                AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC,
                        mFrequence,
                        mPlayChannelConfig, mAudioEncoding, bufferSize,
                        AudioTrack.MODE_STREAM);
                // 开始播放
                track.play();
                //Log.i("play_mq","createok");
                mIsRealTimeTraning=true;
                byte [] b=new byte[2];
                while (mIsRealTimeTraning) {
                    try{
                        tmpBuf=m_in_q2.getFirst();
                        m_in_q2.removeFirst();}
                    catch (Exception e){continue;}
                    int i = 0;
                    //大小端转换
                    while (i < buffer.length) {
                        shortToByte_LH(tmpBuf[i],b);
                        buffer[i] =byteToShort_HL(b);
                        i++;
                    }
                    // 然后将数据写入到AudioTrack中
                    track.write(buffer, 0, buffer.length);
                    //Log.i("play_mq","ok");

                }


                // 播放结束
                track.stop();
                mIsPlaying = false;
                mPlay.setTag(null);
                mPlay.setText("播放录音音频");
                mPlay2.setTag(null);
                mPlay2.setText("播放处理后音频");
            } catch (Exception e) {
                // TODO: handle exception
                Log.e("slack", "error:" + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {

        }

        protected void onPreExecute() {

        }
    }*/
}
