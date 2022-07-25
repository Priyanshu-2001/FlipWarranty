package com.flip.warranty.login.utility

data class Details(
    val __v: Int,
    val _id: String,
    val email: String,
    val hash: String,
    val salt: String,
    val user_blockchain_account_address: String,
    val user_type: String
)