package com.flip.warranty.retailer.repository

import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.retailer.dataModel.NewProductDataModel

abstract class AddNewProductRepository {
    abstract suspend fun addNewProduct(data: NewProductDataModel, token: String): Boolean
    abstract suspend fun signTheUnsignedProduct(data: ProductDetailsData)
}

interface SeeListOfProductsListed {
    suspend fun getProductNumberList(): ArrayList<ProductDetailsData>
    suspend fun buyProduct(data: ProductDetailsData)
}