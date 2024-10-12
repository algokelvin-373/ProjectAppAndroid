package com.algokelvin.conversionsimple.base.model.locale

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "result_table")
data class ConversionResult (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "message1")
    val msg1: String,

    @ColumnInfo(name = "message2")
    val msg2: String
)