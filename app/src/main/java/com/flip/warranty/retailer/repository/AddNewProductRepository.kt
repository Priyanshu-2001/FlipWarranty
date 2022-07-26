package com.flip.warranty.retailer.repository

import com.flip.warranty.retailer.dataModel.NewProductDataModel

abstract class AddNewProductRepository {
    abstract suspend fun addNewProduct(data: NewProductDataModel, token: String): Boolean
}