package app.isfaaghyth.architecture.base

import app.isfaaghyth.architecture.data.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class UseCase<P, R : Any> constructor(
    private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(param: P): Flow<Resource<R>> {
        return execute(param)
            .catch { emit(Resource.error(it.message?: "")) }
            .flowOn(dispatcher)
    }

    @Throws(RuntimeException::class)
    abstract fun execute(param: P): Flow<Resource<R>>

}