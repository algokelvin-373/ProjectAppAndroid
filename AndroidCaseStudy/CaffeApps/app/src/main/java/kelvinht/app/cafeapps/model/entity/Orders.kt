package kelvinht.app.cafeapps.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Orders (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "noTable") val noTable: String,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "price") val price: Int,
)