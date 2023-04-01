package algokelvin.app.dagger2

import algokelvin.app.dagger2.components.DaggerSmartPhoneComponent
import algokelvin.app.dagger2.components.SmartPhone
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var smartPhone: SmartPhone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Using Dagger
        DaggerSmartPhoneComponent.create()
            .getSmartPhone()
            .makeACallWithRecording()

        // This is code DI No Use Dagger 2
        /*
        val smartPhone = SmartPhone(
            Battery(),
            SIMCard(ServiceProvider()),
            MemoryCard()
        ).makeACallWithRecording()
        */

    }
}