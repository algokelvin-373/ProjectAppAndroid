package algokelvin.app.notifications

import algokelvin.app.notifications.databinding.ActivityMainBinding
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val KEY_REPLY = "key_reply"
    private val channelId = "algokelvin.app.notifications.channel01"
    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val description = "This is a demo notification"
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelId, "DemoChannel", description)

        binding.btnShowNotification.setOnClickListener {
            displayNotification()
        }

    }

    private fun displayNotification() {
        val notificationId = 45

        // Action intent notification
        val tapResultIntent = Intent(this, SecondActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Action Button Intent on Notification
        val btnActionIntent = Intent(this, DetailsActivity::class.java)
        val pendingBtnActionIntent = PendingIntent.getActivity(
            this,
            0,
            btnActionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val btnAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "Details", pendingBtnActionIntent).build()

        // Reply on Notification
        val remoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert Your Name")
            build()
        } // for input data
        val replyAction = NotificationCompat.Action.Builder(
            0,
            "REPLY",
            pendingIntent
        ).addRemoteInput(remoteInput)
            .build() // for action reply

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Demo Title")
            .setContentText("This is a demo notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .addAction(btnAction)
            .addAction(replyAction)
            .build()
        notificationManager?.notify(notificationId,notification)
    }

    private fun createNotificationChannel(id: String, name: String, channelDesc: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDesc
            }
            notificationManager?.createNotificationChannel(channel)
        }

    }

}