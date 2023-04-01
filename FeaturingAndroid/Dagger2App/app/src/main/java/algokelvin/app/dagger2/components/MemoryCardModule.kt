package algokelvin.app.dagger2.components

import android.util.Log
import dagger.Module
import dagger.Provides

@Module
class MemoryCardModule(private val memorySize: Int) {

    @Provides
    fun providesMemoryCard(): MemoryCard {
        Log.i("MY_TAG", "Size of memory is $memorySize")
        return MemoryCard()
    }

}