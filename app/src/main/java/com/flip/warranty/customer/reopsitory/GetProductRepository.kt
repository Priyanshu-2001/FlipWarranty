package com.flip.warranty.customer.reopsitory

import androidx.lifecycle.MutableLiveData
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.customer.dataModel.SellinProductToBuyerData
import retrofit2.Response

interface GetProductRepository {
    suspend fun getProductNumberList(): ArrayList<ProductDetailsData>
    suspend fun buyProduct(data: ProductDetailsData): MutableLiveData<Response<SellinProductToBuyerData>>
    suspend fun getSingleProductDetail(serialNumber: String): ProductDetailsData
    suspend fun getOrderHistoryProductList(): ArrayList<ProductDetailsData>
}