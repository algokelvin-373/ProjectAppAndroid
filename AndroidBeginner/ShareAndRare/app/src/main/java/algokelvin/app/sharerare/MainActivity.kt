package algokelvin.app.sharerare

import algokelvin.app.sharerare.databinding.ActivityMainBinding
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRareApps.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://play.google.com/store/apps/details?id=kelvintandrio.algo273.personalco.physiccalculator")
                setPackage("com.android.vending")
            }
            startActivity(intent)
        }

        binding.btnShareApps.setOnClickListener {
            val urlApps = "https://play.google.com/store/apps/details?id=kelvintandrio.algo273.personalco.physiccalculator"

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, urlApps)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share to :"))
        }

    }
}