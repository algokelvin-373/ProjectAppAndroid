package algokelvin.app.dagger2.components

import algokelvin.app.dagger2.MainActivity
import algokelvin.app.dagger2.battery.NCBatteryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MemoryCardModule::class, NCBatteryModule::class])
interface SmartPhoneComponent {
    // for injection on main activity
    fun inject(mainActivity: MainActivity)

    // for injection method getSmartPhone
    /*fun getSmartPhone(): SmartPhone*/
}