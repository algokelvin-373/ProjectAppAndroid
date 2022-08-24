package com.algokelvin.app.firebase.basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_subscribe.setOnClickListener {
            FirebaseMessaging.getInstance().subscribeToTopic("welcome")
            Toast.makeText(this@MainActivity, "You click subscribe", Toast.LENGTH_SHORT).show()
        }

        btn_token.setOnClickListener {
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
                val tokenFirebase = instanceIdResult.token
                val msg = getString(R.string.data_token, tokenFirebase)
                text_token.text = msg
                Log.d("Firebase-token", "You get Token = $tokenFirebase")
            }
        }
    }
}
