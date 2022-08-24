package algorithm.kelvin.app.fragment_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class TwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)
    }

    override fun onStart() {
        super.onStart()
        Log.i("fragment-activity-two", "Start is run")
    }

    override fun onResume() {
        super.onResume()
        Log.i("fragment-activity-two", "Resume is run")
    }

    override fun onPause() {
        super.onPause()
        Log.i("fragment-activity-two", "Pause is run")
    }

    override fun onStop() {
        super.onStop()
        Log.i("fragment-activity-two", "Stop is run")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("fragment-activity-two", "Destroy is run")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("fragment-activity-two", "Restart is run")
    }

}
