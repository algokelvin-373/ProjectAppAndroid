package algorithm.kelvin.app.activity_01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var value = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNext.setOnClickListener { startActivity(Intent(this, MainTwoActivity::class.java)) }
    }

    override fun onStart() {
        super.onStart()
        Log.i("activity-one", "Start is run, value = $value")
    }

    override fun onResume() {
        super.onResume()
        value = 1
        Log.i("activity-one", "Resume is run, value = $value")
    }

    override fun onPause() {
        super.onPause()
        Log.i("activity-one", "Pause is run")
    }

    override fun onStop() {
        super.onStop()
        Log.i("activity-one", "Stop is run")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("activity-one", "Destroy is run")
    }

    override fun onRestart() {
        super.onRestart()
        if (value == 1) Log.i("activity-one", "Restart is run with value 1, value = $value")
        else Log.i("activity-one", "Restart is run with value 0, value = $value")
    }
}
