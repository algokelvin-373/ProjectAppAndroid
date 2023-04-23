package algokelvin.app.conversion.db

import algokelvin.app.conversion.model.ConversionResult
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ConversionResult::class], version = 1)
abstract class ConverterDatabase: RoomDatabase() {
    abstract val converterDao: ConverterDao

    companion object {
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
    }

}