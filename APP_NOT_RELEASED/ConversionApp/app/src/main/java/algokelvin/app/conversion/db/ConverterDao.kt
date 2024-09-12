package algokelvin.app.conversion.db

import algokelvin.app.conversion.model.ConversionResult
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConverterDao {

    @Insert
    suspend fun insertResult(conversionResult: ConversionResult)

    @Delete
    suspend fun deleteResult(conversionResult: ConversionResult)

    @Query("DELETE FROM result_table")
    suspend fun deleteAllResult()

    @Query("SELECT * FROM result_table")
    fun getAllResult(): Flow<List<ConversionResult>>

}