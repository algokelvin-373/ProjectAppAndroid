package com.tsm.recorder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val dir: File = File(Environment.getExternalStorageDirectory().absolutePath + "/soundrecorder/")
    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkNeededPermissions()

        button_play_recording.setOnClickListener {
            if (button_play_recording.text == "Start") {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    ActivityCompat.requestPermissions(this, permissions,0)
                } else {
                    startRecording()
                }
            } else if (button_play_recording.text == "Stop") {
                stopRecording()
            }
        }
    }
    private fun checkNeededPermissions() {
        Log.i(ConstantVal.TAG, "Check Permissions")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(ConstantVal.TAG, "Requesting Permissions")
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO), 0)
        }
    }
    private fun startRecording() {
        mediaRecorder = MediaRecorder()
        mediaRecorder?.reset()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

        if (dir.exists()) {
            createFileRecord()
        } else {
            createDirectory()
            createFileRecord()
        }

        mediaRecorder?.setOutputFile(output)

        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show()
            button_play_recording.text = ("Stop")
        } catch (e: IllegalStateException) {
            Log.e(ConstantVal.TAG, e.printStackTrace().toString())
            e.printStackTrace()
        } catch (e: IOException) {
            Log.e(ConstantVal.TAG, e.printStackTrace().toString())
            e.printStackTrace()
        }
    }

    private fun createDirectory() {
        try {
            Log.i(ConstantVal.TAG, "Create File Direktori")
            val recorderDirectory = File(Environment.getExternalStorageDirectory().absolutePath+"/soundrecorder/")
            recorderDirectory.mkdirs()
        } catch (e: IOException){
            e.printStackTrace()
        }
    }
    private fun createFileRecord() {
        Log.i(ConstantVal.TAG, "Create File Recording")
        count = dir.listFiles().size
        output = Environment.getExternalStorageDirectory().absolutePath + "/soundrecorder/recording"+count+".mp3"
        Log.i(ConstantVal.TAG, output.toString())
    }
    private fun stopRecording(){
        mediaRecorder?.stop()
        mediaRecorder?.reset()
        mediaRecorder?.release()
        mediaRecorder = null
        Toast.makeText(this, "Recording stop", Toast.LENGTH_SHORT).show()
        playRecording(count)
    }
    private fun playRecording(count: Int) {
        val path = Uri.parse(Environment.getExternalStorageDirectory().absolutePath+"/soundrecorder/recording"+count+".mp3")
        val manager: AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if(manager.isMusicActive) {
            Toast.makeText(this, "Another recording is just playing! Wait until it's finished!", Toast.LENGTH_SHORT).show()
        }else {
            val mediaPlayer = MediaPlayer()
            try {
                mediaPlayer.setDataSource(this, path)
                mediaPlayer.prepare()

                mediaPlayer.setOnCompletionListener {
                    Log.i(ConstantVal.TAG, "Record is end")
                    button_play_recording.text = ("Start")
                    button_play_recording.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700))
                }

                mediaPlayer.start()
                button_play_recording.text = ("Play")
                button_play_recording.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
            } catch (e: IOException) {
                Log.e(ConstantVal.TAG, "prepare() failed")
            }
        }
    }
}