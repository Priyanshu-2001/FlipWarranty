package com.flip.warranty.customer.APIs

import com.flip.warranty.customer.dataModel.*
import retrofit2.Response
import retrofit2.http.*

interface GetSerialNumberListApi {

    @GET("/getSerialNumberList")
    suspend fun getSerialNumberListApi(
        @Header("Authorization") token: String
    ): Response<ProductSerialNumberListData>

    @POST("/getProduct/{productSerialNumber}")
    suspend fun getProductDetails(
        @Path("productSerialNumber") SerialNum: String,
        @Header("Authorization") token: String
    ): Response<ProductDetailsData>

    @GET("/getSoldStaus/{productSerialNum}")
    suspend fun getProductSoldStatus(
        @Path("productSerialNum") SerialNum: String,
        @Header("Authorization") token: String
    ): Response<SoldStatusData>

    @POST("/sellProduct")
    suspend fun sellProductApi(
        @Body buyer: BuyNowBuyerDetails,
        @Header("Authorization") token: String
    ): Response<SellinProductToBuyerData>

    @GET("/getWarrantyStatus/{productSerialNum}")
    suspend fun getSignStatus(
        @Path("productSerialNum") SerialNum: String,
        @Header("Authorization") token: String
    ): Response<SellinProductToBuyerData>


}