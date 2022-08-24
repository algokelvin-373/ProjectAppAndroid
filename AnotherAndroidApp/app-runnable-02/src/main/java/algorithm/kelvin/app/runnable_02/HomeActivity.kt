package algorithm.kelvin.app.runnable_02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnSignOut.setOnClickListener {
            val runnableMethod = RunnableMethod(context = this)
            runnableMethod.setValueLogin(false)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
