package algokelvin.app.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class UploadWorker(
    context: Context,
    workerParameters: WorkerParameters
): Worker(context, workerParameters) {

    override fun doWork(): Result {
        return try {
            val count = inputData.getInt(ConstVal.KEY_COUNT_VALUE, 0)
            val start = System.currentTimeMillis()
            for (i in 0..count) {
                Log.i("AlgoKelvin", "Uploading $i")
            }
            val finish = System.currentTimeMillis()

            val message = "Time execution: "+ (finish - start) +" ms"
            val outputData = Data.Builder()
                .putString(ConstVal.KEY_WORKER, message)
                .build()

            Result.success(outputData)
        } catch (e: Exception){
            Result.failure()
        }
    }

}