package algokelvin.app.notifications

import algokelvin.app.notifications.databinding.ActivityNameBinding
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class NameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNameBinding

    private val KEY_REPLY = "key_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val remoteInput = RemoteInput.getResultsFromIntent(this.intent)
        if (remoteInput != null) {
            val name = remoteInput.getCharSequence(KEY_REPLY).toString()
            binding.txtName.text = name
        }

        val channelID = "algokelvin.app.notifications"
        val notificationId = 45

        val repliedNotification = NotificationCompat.Builder(this,channelID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentText("Your reply received")
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(notificationId,repliedNotification)

    }
}