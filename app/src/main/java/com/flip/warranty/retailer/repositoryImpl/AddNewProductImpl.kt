package com.flip.warranty.retailer.repositoryImpl

import android.app.Application
import android.util.Log
import com.flip.warranty.retailer.api.AddNewProductApi
import com.flip.warranty.retailer.dataModel.NewProductDataModel
import com.flip.warranty.retailer.repository.AddNewProductRepository

class AddNewProductImpl(private val api: AddNewProductApi, private val app: Application) :
    AddNewProductRepository() {

    override suspend fun addNewProduct(data: NewProductDataModel, token: String): Boolean {
        Log.e("RepoImpl", "addNewProduct : ${data.display_name} $token")
        val response = api.addNewProductAPI(data, "Bearer $token")
        return if (response.isSuccessful) {
            Log.e("TAG", "AddProduct: Product Added")
            true
        } else {
            Log.e("TAG", "AddProduct: Product Added failed ${response.raw()}")
            false
        }
    }
}