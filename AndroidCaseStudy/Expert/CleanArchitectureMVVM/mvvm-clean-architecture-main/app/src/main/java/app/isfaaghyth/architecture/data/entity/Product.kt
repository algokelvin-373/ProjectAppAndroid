package app.isfaaghyth.architecture.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "product"
)
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id: Int = 0,

    @ColumnInfo(name="name")
    val name: String = "",

    @ColumnInfo(name="price")
    val price: Int = 0
)