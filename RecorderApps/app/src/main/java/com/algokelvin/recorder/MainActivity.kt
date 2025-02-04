package com.algokelvin.recorder

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.algokelvin.recorder.databinding.ActivityMainBinding
import com.tsm.recorder.ConstantVal
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dir: File = File(Environment.getExternalStorageDirectory().absolutePath + "/soundrecorder/")
    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var count = 0

    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO)

    private var recorder: MediaRecorder? = null
    private var outputFilePath: String? = null

    private var isRecording = false
    private lateinit var handler: Handler
    private var startTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //checkNeededPermissions()
        checkPermissions()

        binding.buttonPlayRecording.setOnClickListener {
            if (binding.buttonPlayRecording.text == "Start") {
                /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    ActivityCompat.requestPermissions(this, permissions,0)
                } else {
                    startRecording()
                }*/
                if (checkPermissions()) {
                    startRecording()
                } else {
                    requestPermissions()
                }
            } else if (binding.buttonPlayRecording.text == "Stop") {
                stopRecording()
            }
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
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
        /*mediaRecorder = MediaRecorder()
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
        }*/

        try {
            outputFilePath = getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath + "/recording.mp3"
            recorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(outputFilePath)
                prepare()
                start()
            }
            binding.buttonPlayRecording.text = ("Stop")

            isRecording = true
            startTime = System.currentTimeMillis()
            handler = Handler()
            handler.post(updateTimeRunnable)
            handler.post(updateVisualizer())
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            // Tampilkan pesan kesalahan kepada pengguna
            Toast.makeText(this, "Failed to start recording", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            // Tampilkan pesan kesalahan kepada pengguna
            Toast.makeText(this, "Recording failed due to IO error", Toast.LENGTH_SHORT).show()
        }
    }

    private val updateTimeRunnable = object : Runnable {
        override fun run() {
            if (isRecording) {
                val elapsedTime = System.currentTimeMillis() - startTime
                val seconds = (elapsedTime / 1000) % 60
                val minutes = (elapsedTime / (1000 * 60)) % 60
                binding.txtRecordTime.text = String.format("%02d:%02d", minutes, seconds)
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun updateVisualizer() = object : Runnable {
        override fun run() {
            if (isRecording) {
                val maxAmplitude = recorder?.maxAmplitude?.toFloat() ?: 0f
                binding.visualizerAudio.addAmplitude(maxAmplitude)
                handler.postDelayed(this, 100)
            }
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
        /*mediaRecorder?.stop()
        mediaRecorder?.reset()
        mediaRecorder?.release()
        mediaRecorder = null
        Toast.makeText(this, "Recording stop", Toast.LENGTH_SHORT).show()
        playRecording(count)*/

        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        isRecording = false
        handler.removeCallbacks(updateTimeRunnable)
        handler.removeCallbacks(updateVisualizer())
        saveRecordingToMediaStore(outputFilePath)
        binding.buttonPlayRecording.text = "Start"
        Toast.makeText(this, "Success Recording", Toast.LENGTH_SHORT).show()
    }

    private fun saveRecordingToMediaStore(filePath: String?) {
        filePath?.let {
            val file = File(it)
            val values = ContentValues().apply {
                put(MediaStore.Audio.Media.DISPLAY_NAME, file.name)
                put(MediaStore.Audio.Media.MIME_TYPE, "audio/mpeg")
                put(MediaStore.Audio.Media.RELATIVE_PATH, Environment.DIRECTORY_MUSIC)
                put(MediaStore.Audio.Media.IS_PENDING, 1)
            }

            val resolver = contentResolver
            val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val itemUri = resolver.insert(collection, values)

            itemUri?.let { uri ->
                resolver.openOutputStream(uri)?.use { outputStream ->
                    file.inputStream().use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                values.clear()
                values.put(MediaStore.Audio.Media.IS_PENDING, 0)
                resolver.update(uri, values, null, null)
            }
        }
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
                    binding.buttonPlayRecording.text = ("Start")
                    binding.buttonPlayRecording.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_700))
                }

                mediaPlayer.start()
                binding.buttonPlayRecording.text = ("Play")
                binding.buttonPlayRecording.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
            } catch (e: IOException) {
                Log.e(ConstantVal.TAG, "prepare() failed")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        recorder?.release()
        recorder = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(ConstantVal.TAG, "Permission-Success")
            } else {
                Log.i(ConstantVal.TAG, "Permission-Denied")
            }
        }
    }
}