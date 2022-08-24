package algorithm.kelvin.app.activity_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_two)
    }

    override fun onStart() {
        super.onStart()
        Log.i("activity-two", "Start is run")
    }

    override fun onResume() {
        super.onResume()
        Log.i("activity-two", "Resume is run")
    }

    override fun onPause() {
        super.onPause()
        Log.i("activity-two", "Pause is run")
    }

    override fun onStop() {
        super.onStop()
        Log.i("activity-two", "Stop is run")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("activity-two", "Destroy is run")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("activity-two", "Restart is run")
    }
}
