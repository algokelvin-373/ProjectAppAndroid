package algokelvin.app.daggerhilt.using_dagger

import algokelvin.app.daggerhilt.DaggerDataComponent
import android.app.Application

class App: Application() {
    lateinit var dataComponent: DataComponent

    override fun onCreate() {
        dataComponent = DaggerDataComponent.builder()
            .build()
        super.onCreate()
    }
}