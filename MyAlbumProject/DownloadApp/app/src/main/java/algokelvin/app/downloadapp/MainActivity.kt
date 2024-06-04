package algokelvin.app.downloadapp

import algokelvin.app.downloadapp.databinding.ActivityMainBinding
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val urlApk = "https://pool.apk.aptoide.com/catappult/tamer-android-qrcode-2-53089106-2c8908ee6f1fd8375d63e50c45bfba1c.apk"
    private val fileApk = "tamer.android.qrcode_1.1.apk"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInstall.setOnClickListener {

        }

    }
}