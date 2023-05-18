package algokelvin.app.room.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SubscriberDao {
    @Insert
    fun insert(subscriber: Subscriber): Long

    @Query("SELECT * FROM subscriber_table")
    fun getAll(): List<Subscriber>

    @Query("SELECT * FROM subscriber_table")
    fun getAllData(): LiveData<List<Subscriber>>

    @Update
    fun update(subscriber: Subscriber)

    @Delete
    fun delete(subscriber: Subscriber)

    @Query("DELETE FROM subscriber_table")
    fun deleteAll()
}