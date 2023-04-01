package algokelvin.app.dagger2.components

import algokelvin.app.dagger2.battery.NCBatteryModule
import dagger.Component

@Component(modules = [MemoryCardModule::class, NCBatteryModule::class])
interface SmartPhoneComponent {
    fun getSmartPhone(): SmartPhone
}