package com.algokelvin.app.model

import kotlinx.coroutines.flow.*

/*
* This is the strategy util to make a data request
* supported offline-mode using Flow.
* */
class NetworkBoundResource<T>(
    val query: () -> Flow<T>, // first, getting the data from local db
    val fetch: suspend () -> T, // then, fetch the data from network
    val saveFetchResult: (T) -> Unit, // after getting the data from network, save it into local db
    val onFetchFailed: (Throwable) -> Unit = {}, // handling fetch failed if any
    val isShouldFetch: (T) -> Boolean = { _ -> true } // is it need to fetch to network or not (default: true)
) {

    fun asFlow(): Flow<Resource<T>> = flow {
        val flow = query()
            .onStart { emit(Resource.loading(null)) }
            .flatMapConcat { data ->
                if (isShouldFetch(data)) {
                    emit(Resource.loading(data))

                    try {
                        saveFetchResult(fetch())
                        query().map {
                            Resource.success(it)
                        }
                    } catch (throwable: Throwable) {
                        onFetchFailed(throwable)
                        query().map {
                            Resource.error(throwable.message?: "", null)
                        }
                    }
                } else {
                    query().map {
                        Resource.success(it)
                    }
                }
            }

        emitAll(flow)
    }
}