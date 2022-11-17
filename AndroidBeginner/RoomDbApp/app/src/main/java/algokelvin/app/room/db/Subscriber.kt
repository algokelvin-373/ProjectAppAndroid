package algokelvin.app.room.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Table.SUBSCRIBER_TABLE)
data class Subscriber(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Table.ID)
    val id: Int,

    @ColumnInfo(name = Table.NAME) val name: String,
    @ColumnInfo(name = Table.EMAIL) val email: String
)
