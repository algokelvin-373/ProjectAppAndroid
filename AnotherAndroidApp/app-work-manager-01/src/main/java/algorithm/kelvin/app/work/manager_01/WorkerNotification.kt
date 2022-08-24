package algorithm.kelvin.app.work.manager_01

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerNotification(val context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        return getNotification()
    }

    fun getNotification(): Result {
        context.notificationsManager("Test Notification")
        return Result.success()
    }
}