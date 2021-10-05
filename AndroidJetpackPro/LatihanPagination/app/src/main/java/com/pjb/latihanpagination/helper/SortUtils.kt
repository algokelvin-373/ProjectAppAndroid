package com.pjb.latihanpagination.helper

import androidx.sqlite.db.SimpleSQLiteQuery
import java.lang.StringBuilder

object SortUtils {

    const val NEWEST = "Newest"
    const val OLDEST = "Oldest"
    const val RANDOM = "Random"

    fun getSortedQuery(filterType: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM note_tbl ")
        when (filterType) {
            NEWEST -> {
                simpleQuery.append("ORDER BY id DESC")
            }
            OLDEST -> {
                simpleQuery.append("ORDER BY id ASC")
            }
            RANDOM -> {
                simpleQuery.append("ORDER BY RANDOM()")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}