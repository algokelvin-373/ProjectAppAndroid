package algokelvin.app.dagger2

import algokelvin.app.dagger2.components.DaggerSmartPhoneComponent
import algokelvin.app.dagger2.components.MemoryCardModule
import algokelvin.app.dagger2.components.SmartPhone
import algokelvin.app.dagger2.components.SmartPhoneApplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var smartPhone: SmartPhone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Using Dagger - Inject with Application Android
        (application as SmartPhoneApplication).smartPhoneComponent
            .inject(this)
        smartPhone.makeACallWithRecording()

        // Using Dagger - Inject with module which has parameter
        /*DaggerSmartPhoneComponent.builder()
            .memoryCardModule(MemoryCardModule(1000))
            .build()
            .inject(this)*/

        // Using Dagger - Inject with this activity
        /*DaggerSmartPhoneComponent.create()
            .inject(this)
        smartPhone.makeACallWithRecording()*/

        // Using Dagger - Inject function program
        /*DaggerSmartPhoneComponent.create()
            .getSmartPhone()
            .makeACallWithRecording()*/

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