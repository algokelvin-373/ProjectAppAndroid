package com.tsm.aptoide.services

import algokelvin.app.downloadapps.R
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.util.Log
import android.widget.Toast
import java.io.File
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.tsm.aptoide.packagehelper.InstallApk

class DownloadServices : Service() {

    private lateinit var url: String
    private lateinit var fileName: String
    private lateinit var packageAppName: String
    private lateinit var appName: String
    private var versionCode: Long = 0

    //private lateinit var downloadTask: ANRequest<*>
    private var downloadTask: FileDownloader? = null
    private val intentBroadcast = Intent()

    private lateinit var notifBuilder: NotificationCompat.Builder

    private var idDownload = 0
    private lateinit var sharedPref: SharedPreferences

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        FileDownloader.setup(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        sharedPref = getSharedPreferences("install_mode_on", Context.MODE_PRIVATE)
        url = intent.getStringExtra("url").toString()
        fileName = intent.getStringExtra("file_name").toString()
        packageAppName = intent.getStringExtra("package_name").toString()
//        image = intent.getStringExtra("image")
        appName = intent.getStringExtra("name_app").toString()

        versionCode = intent.getLongExtra("version_code", 0)

        //val intentBroadcast = Intent()
        intentBroadcast.action = "download"

        val intentStop = Intent(this, StopDownloadServices::class.java)
        intentStop.putExtra("version_code", versionCode)
        val stopAction = PendingIntent.getBroadcast(this, 1, intentStop, PendingIntent.FLAG_UPDATE_CURRENT)

        notifBuilder = NotificationCompat.Builder(this, "2324")
        notifBuilder.setContentTitle("Downloading...")
        notifBuilder.setContentText(appName)
        notifBuilder.setSmallIcon(R.drawable.ic_download_anim_list)
        notifBuilder.setVibrate(null)
        notifBuilder.setOngoing(true)
        notifBuilder.addAction(R.drawable.ic_clear, "Cancel", stopAction)

        downloadTask = FileDownloader.getImpl()

        val listener = object : FileDownloadListener() {
            override fun warn(task: BaseDownloadTask?) {

            }

            override fun completed(task: BaseDownloadTask) {
                NotificationManagerCompat.from(this@DownloadServices).apply {
                    cancel(versionCode.toInt())

                    val file = File(dir(), fileName)

                    if (file.exists()) {

                        val intentApk = Intent(this@DownloadServices, InstallApk::class.java)
                        intentApk.putExtra("file_uri", fileName)
                        intentApk.putExtra("vercode", versionCode)
                        intentApk.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intentApk)

                    } else {
                        Toast.makeText(this@DownloadServices, "gak ada", Toast.LENGTH_SHORT).show()
                    }
                }

                sharedPref.edit().putBoolean("wait", false).apply()

            }

            override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

            }

            override fun error(task: BaseDownloadTask?, e: Throwable?) {
                loge("error message -> ${e?.localizedMessage}")
            }

            override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                logi("progress detail -> $soFarBytes of $totalBytes")
                sharedPref.edit().putBoolean("wait", true).apply()

                NotificationManagerCompat.from(this@DownloadServices).apply {
                    notifBuilder.setProgress(totalBytes, soFarBytes, false)
                    notify(versionCode.toInt(), notifBuilder.build())
                }

                intentBroadcast.putExtra("progress_state", soFarBytes)
                intentBroadcast.putExtra("progress_max", totalBytes)
                intentBroadcast.putExtra("identifier", packageAppName)
                intentBroadcast.putExtra("is_download", true)

                if (soFarBytes == totalBytes) {
                    intent.putExtra("id_download", false)
                    sharedPref.edit().putBoolean("wait", false).apply()
                }

                sendBroadcast(intentBroadcast)
            }

            override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                NotificationManagerCompat.from(this@DownloadServices).apply {
                    cancel(versionCode.toInt())
                }
            }

        }

        val newPath = dir().path + "/" + fileName

        downloadTask?.let { fileDownloader ->

            idDownload = fileDownloader.create(url)
                .setPath(newPath)
                .setListener(listener)
                .start()
        }

        logi("download id -> $idDownload")

        return START_STICKY
    }

    override fun onDestroy() {
        logi("service destroying")

        downloadTask?.clear(idDownload, dir().path+fileName)
        intentBroadcast.putExtra("identifier", packageAppName)
        sendBroadcast(intentBroadcast)
        super.onDestroy()
    }



}