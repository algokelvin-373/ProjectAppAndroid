package algokelvin.app.conversion.repository

import algokelvin.app.conversion.db.ConverterDao
import algokelvin.app.conversion.model.ConversionResult
import kotlinx.coroutines.flow.Flow

class ConverterDatabaseRepositoryImpl(private val dao: ConverterDao): ConverterDatabaseRepository {
    override suspend fun insertResult(conversionResult: ConversionResult) {
        dao.insertResult(conversionResult)
    }

    override suspend fun deleteResult(conversionResult: ConversionResult) {
        dao.deleteResult(conversionResult)
    }

    override suspend fun deleteAllResult() {
        dao.deleteAllResult()
    }

    override fun getAllResult(): Flow<List<ConversionResult>> {
        return dao.getAllResult()
    }
}