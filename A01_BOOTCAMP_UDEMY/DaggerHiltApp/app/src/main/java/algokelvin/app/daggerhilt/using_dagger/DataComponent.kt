package algokelvin.app.daggerhilt.using_dagger

import algokelvin.app.daggerhilt.MainDaggerActivity
import dagger.Component

@Component(modules = [DataModule::class])
interface DataComponent {
    fun inject(mainDaggerActivity: MainDaggerActivity)
}