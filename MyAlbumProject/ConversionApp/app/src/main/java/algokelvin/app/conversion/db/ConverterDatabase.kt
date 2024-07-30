package algokelvin.app.conversion.db

import algokelvin.app.conversion.model.ConversionResult
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ConversionResult::class], version = 1)
abstract class ConverterDatabase: RoomDatabase() {
    abstract val converterDao: ConverterDao

    // Using Dagger Hilt. So, this is not use again
    /*companion object {
        @Volatile
        private var INSTANCE: ConverterDatabase? = null

        fun getInstance(context: Context): ConverterDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ConverterDatabase::class.java,
                        "result_database"
                    ).build()
                }
                return instance
            }
        }
    }*/

}