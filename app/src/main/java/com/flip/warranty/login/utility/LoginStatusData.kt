package com.flip.warranty.login.utility

data class LoginStatusData(
    val details: Details,
    val status: String,
    val success: Boolean,
    val token: String
)