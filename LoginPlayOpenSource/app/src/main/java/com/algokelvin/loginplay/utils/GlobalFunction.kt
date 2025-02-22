package com.algokelvin.loginplay.utils

object GlobalFunction {
    fun validateEmail(email: String): Boolean{
        return email.contains("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex())
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }
}