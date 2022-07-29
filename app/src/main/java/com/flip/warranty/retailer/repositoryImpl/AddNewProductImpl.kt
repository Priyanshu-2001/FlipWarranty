package com.flip.warranty.retailer.repositoryImpl

import android.content.SharedPreferences
import android.util.Log
import com.flip.warranty.customer.APIs.GetSerialNumberListApi
import com.flip.warranty.customer.dataModel.BuyNowBuyerDetails
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
            response.body()?.data?.forEach {
                val res = serialNumberApi.getProductDetails(
                    it.serial_number,
                    token
                )
                if (res.isSuccessful) {
                    val soldRes = serialNumberApi.getProductSoldStatus(
                        it.serial_number,
                        token
                    )
                    val signRes = serialNumberApi.getSignStatus(
                        it.serial_number,
                        token
                    )
                    if (soldRes.isSuccessful) {
                        val item = res.body()!!
                        item.soldStatus = soldRes.body()?.soldStatus ?: "0"
                        item.serialNUmber = it.serial_number
                    } else {
                        Log.e(TAG, "errro getProductNumberList: ${soldRes.raw()}")
                    }
                    if (signRes.isSuccessful) {
                        val item = res.body()!!
                        item.signStatus = soldRes.body()?.soldStatus ?: "0"
                        list.add(
                            item
                        )
                    } else {
                        Log.e(TAG, "error getProductNumberList: ${soldRes.raw()}")
                    }
                } else {
                    res.headers()
                    res.message()
                }
            }
        }
        return list
    }

    override suspend fun buyProduct(data: ProductDetailsData) {
        sharedPreferences.getString("blockChainAddress", " ")?.let {
            BuyNowBuyerDetails(
                serial_number = data.serialNUmber,
                new_owner = it
            )
        }?.let {
            val res = serialNumberApi.sellProductApi(it, token)
            if (res.isSuccessful) {
                Log.e(
                    TAG,
                    "buyProduct: Success " + (res.body()?.transactionHash?.receipt?.blockHash
                        ?: " ")
                )
            } else {
                Log.e(TAG, "buyProduct: failed to Buy " + res.raw())
                Log.e(TAG, "buyProduct: failed to Buy ${it.new_owner}")
            }
        }

    }

}