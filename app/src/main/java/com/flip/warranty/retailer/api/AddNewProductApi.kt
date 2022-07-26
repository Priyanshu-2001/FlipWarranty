package com.flip.warranty.retailer.api

import com.flip.warranty.retailer.dataModel.NewProductDataModel
import com.flip.warranty.utility.Globals
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AddNewProductApi {

    @POST(Globals.CreateNewItem)
    suspend fun addNewProductAPI(
        @Body data: NewProductDataModel,
        @Header("Authorization") token: String
    ): Response<NewProductDataModel>
}