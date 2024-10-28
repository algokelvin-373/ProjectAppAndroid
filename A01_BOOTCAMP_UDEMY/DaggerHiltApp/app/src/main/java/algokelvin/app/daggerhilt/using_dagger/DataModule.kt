package algokelvin.app.daggerhilt.using_dagger

import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providesDataSource(): DataSource {
        return DataSource()
    }

}