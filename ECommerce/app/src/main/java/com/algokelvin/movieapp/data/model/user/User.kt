package com.algokelvin.movieapp.data.model.user

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class NamePerson(
    @SerializedName("firstname")
    @ColumnInfo("firstname")
    val firstName: String,

    @SerializedName("lastname")
    @ColumnInfo("lastname")
    val lastName: String,
)

@Entity(tableName = "users_data")
data class User(
    @SerializedName("id")
    @PrimaryKey
    val id: Int,

    @SerializedName("email")
    @ColumnInfo("user_email")
    val email: String,

    @SerializedName("username")
    @ColumnInfo("user_username")
    val username: String,

    @SerializedName("password")
    @ColumnInfo("user_password")
    val password: String,

    @SerializedName("name")
    @Embedded(prefix = "user_")
    val name: NamePerson,

    @SerializedName("phone")
    @ColumnInfo("user_phone")
    val phone: String,
)