package kelvinht.app.cafeapps.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kelvinht.app.cafeapps.model.entity.Orders

@Dao
interface OrdersDao {
    @Insert
    fun insertOrder(order: Orders)

    @Query("SELECT * FROM orders")
    fun getOrders(): List<Orders>

    @Query("SELECT id FROM orders WHERE noTable = :noTable")
    fun getId(noTable: String): Int

    @Delete
    fun delete(order: Orders)
}