package algorithm.kelvin.app.runnable_02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSignIn.setOnClickListener {
            val handler = Handler()
            val runnableMethod = RunnableMethod(handler, this)
            runnableMethod.startRunnable()
            runnableMethod.setValueLogin(true)
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
