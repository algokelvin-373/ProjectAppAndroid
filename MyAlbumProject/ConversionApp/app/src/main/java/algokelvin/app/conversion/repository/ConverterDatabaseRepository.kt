package algokelvin.app.conversion.repository

import algokelvin.app.conversion.model.ConversionResult
import kotlinx.coroutines.flow.Flow

interface ConverterDatabaseRepository {
    suspend fun insertResult(conversionResult: ConversionResult)
    suspend fun deleteResult(conversionResult: ConversionResult)
    suspend fun deleteAllResult()
    fun getAllResult(): Flow<List<ConversionResult>>
}