package app.isfaaghyth.architecture.data

import kotlinx.coroutines.flow.*

/*
* This is the strategy util to make a data request
* supported offline-mode using Flow.
* */
class NetworkBoundResource<T>(
    // first, getting the data from local db
    val query: () -> Flow<T>,

    // then, fetch the data from network
    val fetch: suspend () -> T,

    // after getting the data from network, save it into local db
    val saveFetchResult: (T) -> Unit,

    // handling fetch failed if any
    val onFetchFailed: (Throwable) -> Unit = {},

    // is it need to fetch to network or not (default: true)
    val isShouldFetch: (T) -> Boolean = { _ -> true }
) {

    fun asFlow(): Flow<Resource<T>> = flow {
        val flow = query()
            .onStart { emit(Resource.loading(null)) }
            .flatMapConcat { data ->
                if (isShouldFetch(data)) {
                    emit(Resource.loading(data))

                    try {
                        saveFetchResult(fetch())
                        query().map { Resource.success(it) }
                    } catch (throwable: Throwable) {
                        onFetchFailed(throwable)
                        query().map {
                            Resource.error(
                                throwable.message?: "", null
                            )
                        }
                    }
                } else {
                    query().map { Resource.success(it) }
                }
            }

        emitAll(flow)
    }
}