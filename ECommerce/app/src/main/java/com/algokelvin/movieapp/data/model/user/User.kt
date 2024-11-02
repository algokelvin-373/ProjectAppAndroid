package com.algokelvin.movieapp.data.model.user

import com.google.gson.annotations.SerializedName

data class NamePerson(
    @SerializedName("firstname")
    val firstName: String,

    @SerializedName("lastname")
    val lastName: String,
)

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("email")
    val email: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("name")
    val name: NamePerson,

    @SerializedName("phone")
    val phone: String,
)