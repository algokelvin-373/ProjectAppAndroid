package algorithm.kelvin.app.notification_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShowNotification.setOnClickListener {
            notificationsManager("You get notifications. Click here to open activity", "Success Notifications")
        }
    }
}
