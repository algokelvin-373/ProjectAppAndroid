package algorithm.kelvin.app.work.manager_01

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.ListenableWorker

fun Context.notificationsManager(message: String?): ListenableWorker.Result {
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val mBuilder = NotificationCompat.Builder(this, "ALGOKELVIN")
        .setSmallIcon(R.drawable.ic_bell)
        .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_bell))
        .setContentTitle("ALGOKELVIN")
        .setContentText(message)
        .setSubText("You get notification")
        .setAutoCancel(true)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel("ALGOKELVIN", "ALGOKELVIN Notification", NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = message
        mBuilder.setChannelId("ALGOKELVIN")
        notificationManager.createNotificationChannel(channel)
    }

    val notification = mBuilder.build()
    notificationManager.notify(1, notification)

    return ListenableWorker.Result.success()
}