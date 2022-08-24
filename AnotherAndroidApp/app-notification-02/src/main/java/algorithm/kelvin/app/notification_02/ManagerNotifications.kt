package algorithm.kelvin.app.notification_02

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import java.util.*
import android.app.PendingIntent

fun Context.notificationsManager(message: String?, subtext: String?) {
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val intentNotification = Intent(this, MainActivity::class.java)
    intentNotification.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    val intent = PendingIntent.getActivity(this, 0, intentNotification, PendingIntent.FLAG_UPDATE_CURRENT)

    val mBuilder = NotificationCompat.Builder(this, "AlgoKelvin")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background))
        .setContentIntent(intent)
        .setContentTitle("AlgoKelvin")
        .setContentText(message)
        .setSubText(subtext)
        .setAutoCancel(true)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel("AlgoKelvin", "AlgoKelvin Notification", NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = message
        mBuilder.setChannelId("AlgoKelvin")
        notificationManager.createNotificationChannel(channel)
    }

    val notification = mBuilder.build()
    notificationManager.notify(Random().nextInt(100), notification)
}