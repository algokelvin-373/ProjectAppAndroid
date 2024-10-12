package kelvinht.app.cafeapps.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kelvinht.app.cafeapps.model.entity.Orders

@Database(entities = [Orders::class], version = 1)
abstract class CaffeDb: RoomDatabase() {
    abstract fun ordersDao(): OrdersDao

    companion object {
        private var INSTANCE: CaffeDb? = null

        fun getDb(context: Context): CaffeDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CaffeDb::class.java,
                    "caffe_db"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}