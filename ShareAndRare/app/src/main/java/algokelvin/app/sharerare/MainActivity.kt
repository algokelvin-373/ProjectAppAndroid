package algokelvin.app.sharerare

import algokelvin.app.sharerare.databinding.ActivityMainBinding
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val urlPlayStoreLinkedIn = "https://play.google.com/store/apps/details?id=com.linkedin.android"
    private val urlSharing = "https://www.linkedin.com/in/kelvin-herwanda-tandrio/"
    private val linkedInApp = "com.linkedin.android"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRareApps.setOnClickListener {
            if (isAppInstalled(this, linkedInApp)) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlSharing))
                intent.setPackage(linkedInApp) // Directly open LinkedIn app if installed
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "Unable to open LinkedIn app.", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            } else {
                redirectToPlayStore()
            }
        }

        binding.btnShareApps.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, urlSharing)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share to :"))
        }

    }

    private fun redirectToPlayStore() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$linkedInApp")))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlPlayStoreLinkedIn)))
        }
    }

    private fun isAppInstalled(context: Context, packageName: String): Boolean {
        val pm = context.packageManager
        return try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}