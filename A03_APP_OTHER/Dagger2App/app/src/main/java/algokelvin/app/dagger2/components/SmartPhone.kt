package algokelvin.app.dagger2.components

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmartPhone @Inject constructor(
    battery: Battery,
    simCard: SIMCard,
    memoryCard: MemoryCard
) {

    init {
        battery.getPower()
        simCard.getConnection()
        memoryCard.getSpaceAvailablity()
        Log.i("MYTAG", "SmartPhone Constructed")
    }

    fun makeACallWithRecording() {
        Log.i("MYTAG", "Calling.....")
    }
}

