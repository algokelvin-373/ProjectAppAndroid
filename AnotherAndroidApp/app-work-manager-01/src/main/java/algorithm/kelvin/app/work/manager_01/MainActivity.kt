package algorithm.kelvin.app.work.manager_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.*

class MainActivity : AppCompatActivity() {
    private lateinit var periodicWorkRequest: PeriodicWorkRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runPeriodicTask()
    }

    private fun runPeriodicTask() {
        val data = Data.Builder().putString("Algokelvin", "Algokelvin").build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        periodicWorkRequest = PeriodicWorkRequest.Builder(WorkerNotification::class.java, 15, java.util.concurrent.TimeUnit.MINUTES)
            .setInputData(data)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueue(periodicWorkRequest)
//        WorkManager.getInstance().getWorkInfoByIdLiveData(periodicWorkRequest.id).observe(this@MainActivity,
//            Observer<WorkInfo> { workInfo ->
//                val status = workInfo.state.name
//                Log.i("WorkManager", status)
//            })
    }
}