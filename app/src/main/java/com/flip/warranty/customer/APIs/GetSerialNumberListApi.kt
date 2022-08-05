package com.flip.warranty.customer.APIs

import com.flip.warranty.customer.dataModel.*
import com.flip.warranty.utility.Globals
import retrofit2.Response
import retrofit2.http.*

interface GetSerialNumberListApi {

    @GET(Globals.GetSerialNumberList)
    suspend fun getSerialNumberListApi(
        @Header("Authorization") token: String
    ): Response<ProductSerialNumberListData>

    @POST(Globals.GetProductBySerialNumber + "{productSerialNumber}")
    suspend fun getProductDetails(
        @Path("productSerialNumber") SerialNum: String,
        @Header("Authorization") token: String
    ): Response<ProductDetailsData>

    @GET(Globals.GetSoldStatus + "{productSerialNum}")
    suspend fun getProductSoldStatus(
        @Path("productSerialNum") SerialNum: String,
        @Header("Authorization") token: String
    ): Response<SoldStatusData>

    @POST(Globals.SellProduct)
    suspend fun sellProductApi(
        @Body buyer: BuyNowBuyerDetails,
        @Header("Authorization") token: String
    ): Response<SellinProductToBuyerData>

    @GET(Globals.GetProductWarrantyStatus + "{productSerialNum}")
    suspend fun getSignStatus(
        @Path("productSerialNum") SerialNum: String,
        @Header("Authorization") token: String
    ): Response<WarrantyStatusResponseData>

    @GET(Globals.GetOwnedProductList)
    suspend fun getOrderHistoryApi(
        @Header("Authorization") token: String
    ): Response<orderHistoryResponseData>


}