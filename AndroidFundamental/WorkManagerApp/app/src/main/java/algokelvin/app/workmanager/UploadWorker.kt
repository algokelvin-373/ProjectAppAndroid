package algokelvin.app.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class UploadWorker(
    context: Context,
    workerParameters: WorkerParameters
): Worker(context, workerParameters) {

    override fun doWork(): Result {
        return try {
            for (i in 0..600) {
                Log.i("AlgoKelvin", "Uploading $i")
            }
            Result.success()
        } catch (e: Exception){
            Result.failure()
        }
    }

}