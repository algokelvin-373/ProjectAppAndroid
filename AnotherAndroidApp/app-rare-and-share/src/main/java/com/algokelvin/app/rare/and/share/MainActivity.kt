package com.algokelvin.app.rare.and.share

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRareApps.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://play.google.com/store/apps/details?id=kelvintandrio.algo273.personalco.physiccalculator")
                setPackage("com.android.vending")
            }
            startActivity(intent)
        }

        btnShareApps.setOnClickListener {
            val urlApps = "https://play.google.com/store/apps/details?id=kelvintandrio.algo273.personalco.physiccalculator"

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, urlApps)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share to :"))
        }
    }
}
