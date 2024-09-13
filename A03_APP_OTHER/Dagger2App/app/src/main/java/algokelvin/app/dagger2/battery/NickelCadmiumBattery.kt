package algokelvin.app.dagger2.battery

import algokelvin.app.dagger2.components.Battery
import android.util.Log
import javax.inject.Inject

class NickelCadmiumBattery @Inject constructor() : Battery {
    override fun getPower() {
        Log.i("MY_TAG", "Power from Nickel Cadmium Battery")
    }
}