package app.isfaaghyth.architecture.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.isfaaghyth.architecture.data.entity.Product

@Database(
    entities = [Product::class],
    version = 1
)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        private const val DB_NAME = "product_data"
        @Volatile private var INSTANCE: ProductDatabase? = null

        fun instance(context: Context): ProductDatabase {
            return INSTANCE?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}