package com.flip.warranty.login.repository

import com.flip.warranty.login.utility.LoginCred

interface LoginRepository {
    suspend fun LoginUser(cred: LoginCred): Boolean
}