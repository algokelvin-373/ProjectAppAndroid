package algokelvin.app.dagger2.components

import dagger.Component

@Component(modules = [MemoryCardModule::class])
interface SmartPhoneComponent {
    fun getSmartPhone(): SmartPhone
}