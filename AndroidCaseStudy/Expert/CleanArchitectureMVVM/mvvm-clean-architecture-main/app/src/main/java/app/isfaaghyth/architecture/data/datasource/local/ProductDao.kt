package app.isfaaghyth.architecture.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.isfaaghyth.architecture.data.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao interface ProductDao {

    @Query("SELECT * FROM product")
    fun products(): Flow<List<Product>>

    @JvmSuppressWildcards
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)

    @Insert
    fun insert(product: Product)

    @Query("SELECT * FROM product WHERE id=:id")
    fun productBy(id: Int): Flow<Product>

}