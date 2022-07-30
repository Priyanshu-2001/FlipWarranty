package com.flip.warranty.retailer.repositoryImpl

import android.content.SharedPreferences
import android.util.Log
import com.flip.warranty.customer.APIs.GetSerialNumberListApi
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.retailer.api.AddNewProductApi
import com.flip.warranty.retailer.dataModel.NewProductDataModel
import com.flip.warranty.retailer.dataModel.SignTheNewProduct
import com.flip.warranty.retailer.repository.AddNewProductRepository
import com.flip.warranty.retailer.repository.SeeListOfProductsListed
import com.flip.warranty.utility.Globals.TAG

class AddNewProductImpl(
    private val api: AddNewProductApi,
    val sharedPreferences: SharedPreferences,
    private val serialNumberApi: GetSerialNumberListApi
) :
    AddNewProductRepository(), SeeListOfProductsListed {
    val token = "Bearer " + sharedPreferences.getString("token", " ")!!

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

    override suspend fun signTheUnsignedProduct(data: ProductDetailsData) {
        val res = api.signNewProductApi(
            SignTheNewProduct(
                "Condition Apply as Per Policy",
                (System.currentTimeMillis() / 1000L).toString(),
                data.serialNUmber,
                (System.currentTimeMillis() / 1000L).toString()
            ), token
        )
        if (res.isSuccessful) {
            Log.e(TAG, "signTheUnsignedProduct: " + res.message())
        } else {
            Log.e(TAG, "buyProduct: failed to Sign " + res.raw())
        }
    }

    override suspend fun getProductNumberList(): ArrayList<ProductDetailsData> {
        val response =
            serialNumberApi.getSerialNumberListApi(token)
        val list = ArrayList<ProductDetailsData>()
        if (response.isSuccessful) {
            response.body()?.product_list?.forEach {
                val productDetails = serialNumberApi.getProductDetails(
                    it,
                    token
                )
                if (productDetails.isSuccessful) {
                    val soldRes = serialNumberApi.getProductSoldStatus(
                        it,
                        token
                    )
                    val signRes = serialNumberApi.getSignStatus(
                        it,
                        token
                    )
                    if (soldRes.isSuccessful) {
                        productDetails.body()!!.soldStatus = soldRes.body()?.soldStatus ?: "0"
                        productDetails.body()!!.serialNUmber = it
                    } else {
                        Log.e(TAG, "error getProductNumberList: ${soldRes.raw()}")
                    }
                    if (signRes.isSuccessful) {
                        println()
                        Log.e(
                            TAG,
                            "getProductNumberList: signed res = " + signRes.body()?.WarrantyStatus.toString()
                        )
                        Log.e(
                            TAG,
                            "getProductNumberList: sold res = " + productDetails.body()!!.soldStatus
                        )
                        Log.e(
                            TAG,
                            "getProductNumberList: name res = " + productDetails.body()!!.prodDisplayName
                        )
                        Log.e(
                            TAG,
                            "getProductNumberList: name res = " + productDetails.body()!!.manufacturer
                        )
                        productDetails.body()!!.signStatus = signRes.body()?.WarrantyStatus ?: "0"
                    } else {
                        Log.e(TAG, "error getProductNumberList:signed res  ${signRes.raw()}")
                    }
                    list.add(
                        productDetails.body()!!
                    )
                } else {
                    productDetails.headers()
                    productDetails.message()
                }
            }
        }
        return list
    }

    override suspend fun buyProduct(data: ProductDetailsData) {
        TODO("Not yet implemented")
    }

}