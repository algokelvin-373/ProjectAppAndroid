package algokelvin.app.workmanager

import algokelvin.app.workmanager.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            setOneTimeWorkRequest()
        }

    }

    private fun setOneTimeWorkRequest() {
        val workManager = WorkManager.getInstance(applicationContext) // Set Work Manager

        val data = Data.Builder()
            .putInt(ConstVal.KEY_COUNT_VALUE, 1500)
            .build() // Send data integer using Work Manager
        val constraints = Constraints.Builder()
            .setRequiresCharging(true) // Work is run if charge is working
            .setRequiredNetworkType(NetworkType.CONNECTED) // Work is run if network is connected
            .build()

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        workManager.enqueue(uploadRequest)
        workManager.getWorkInfoByIdLiveData(uploadRequest.id).observe(this) {
            binding.txtData.text = it.state.name
            if (it.state.isFinished) {
                val dataOutput = it.outputData
                val message = dataOutput.getString(ConstVal.KEY_WORKER)
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            }
        }
    }

}