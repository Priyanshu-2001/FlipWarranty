package com.flip.warranty.login.utility

import com.flip.warranty.utility.Globals
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {
    @POST(Globals.Login)
    suspend fun Login(@Body loginCred: LoginCred): Response<LoginStatusData>
}