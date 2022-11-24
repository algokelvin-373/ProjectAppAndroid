package algokelvin.app.workmanager

import algokelvin.app.workmanager.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val workManager = WorkManager.getInstance(applicationContext)
        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java).build()
        workManager.enqueue(uploadRequest)
        workManager.getWorkInfoByIdLiveData(uploadRequest.id).observe(this) {
            binding.txtData.text = it.state.name
        }
    }

}