package algokelvin.app.dagger2.battery

import algokelvin.app.dagger2.components.Battery
import dagger.Binds
import dagger.Module
import dagger.Provides

// Using Module and Provides
/*@Module
class NCBatteryModule {
    @Provides
    fun providesNCBattery(nickelCadmiumBattery: NickelCadmiumBattery): Battery {
        return nickelCadmiumBattery
    }
}*/

// Using Module, abstract class and function, Binds
@Module
abstract class NCBatteryModule {
    @Binds
    abstract fun providesNCBattery(nickelCadmiumBattery: NickelCadmiumBattery): Battery
}