package com.flip.warranty.customer.APIs

import com.flip.warranty.customer.dataModel.WarrantyDetails
import com.flip.warranty.utility.Globals
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface WarrantyDetailsAPI {

    @POST(Globals.GetProductWarranty + "{SerialNumber}")
    suspend fun getWarrantyDetailsAPI(
        @Path("SerialNumber") SerialNum: String,
        @Header("Authorization") token: String
    ): Response<WarrantyDetails>
}