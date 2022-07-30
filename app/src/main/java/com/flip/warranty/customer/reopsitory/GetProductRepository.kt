package com.flip.warranty.customer.reopsitory

import com.flip.warranty.customer.dataModel.ProductDetailsData

interface GetProductRepository {
    suspend fun getProductNumberList(): ArrayList<ProductDetailsData>
    suspend fun buyProduct(data: ProductDetailsData)
    suspend fun getSingleProductDetail(serialNumber: String): ProductDetailsData
    suspend fun getOrderHistoryProductList(): ArrayList<ProductDetailsData>
}