package com.algokelvin.app.base

import com.algokelvin.app.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class UseCase<P, R: Any>(private val dispatcher: CoroutineDispatcher) {

    operator fun invoke(param: P): Flow<Resource<R>> {
        return execute(param).catch {
            emit(Resource.error(it.message ?: ""))
        }.flowOn(dispatcher)
    }

    @Throws(RuntimeException::class)
    abstract fun execute(param: P): Flow<Resource<R>>

}