package com.tsm.aptoide.packagehelper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.tsm.aptoide.services.StopDownloadServices
import com.tsm.base.dir
import java.io.File

class InstallApk : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.getStringExtra("file_uri")?.let { fileName ->
            val vercode = intent.getLongExtra("vercode", 0)
            val file: File? = File(dir(), fileName)

            if (file != null) {
                Log.i("Aptoid-Install", "File is $file")
                val fileUri = FileProvider.getUriForFile(this.applicationContext, "com.tsm.store", file)
                Log.i("Aptoid-Install", "FileUri is $fileUri")

                val intent = Intent(this, StopDownloadServices::class.java)
                intent.putExtra("version_code", vercode)
                sendBroadcast(intent)

                val intentInstall = Intent(Intent.ACTION_VIEW)
                intentInstall.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                intentInstall.data = fileUri
                startActivityForResult(intentInstall, 2)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentUpdate = Intent()
        intentUpdate.action = "update_view"
        intentUpdate.putExtra("update_view", true)
        sendBroadcast(intentUpdate)
        finish()
    }
}
