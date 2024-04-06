package algokelvin.app.daggerhilt

import algokelvin.app.daggerhilt.using_dagger.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModuleForHilt {

    @Provides
    fun providesDataSource(): DataSource {
        return DataSource()
    }

}