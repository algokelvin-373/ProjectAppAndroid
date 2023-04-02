package algokelvin.app.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = 1, exportSchema = false)
abstract class SubscriberDB: RoomDatabase() {
    abstract fun subscriberDao(): SubscriberDao

    companion object {
        private var INSTANCE: SubscriberDB? = null

        fun getDb(context: Context): SubscriberDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SubscriberDB::class.java,
                    "subscriber_db"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}