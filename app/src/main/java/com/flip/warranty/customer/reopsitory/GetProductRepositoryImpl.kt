package com.flip.warranty.customer.reopsitory

import android.content.SharedPreferences
import android.util.Log
import com.flip.warranty.customer.APIs.GetSerialNumberListApi
import com.flip.warranty.customer.dataModel.BuyNowBuyerDetails
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.utility.Globals.TAG

class GetProductRepositoryImpl(
    val api: GetSerialNumberListApi,
    val sharedPreferences: SharedPreferences
) : GetProductRepository {
    val token = "Bearer " + sharedPreferences.getString("token", " ")!!

    override suspend fun getProductNumberList(): ArrayList<ProductDetailsData> {
        val response =
            api.getSerialNumberListApi(token)
        val list = ArrayList<ProductDetailsData>()
        if (response.isSuccessful) {
            response.body()?.data?.forEach {
                val res = api.getProductDetails(
                    it.serial_number,
                    token
                )
                if (res.isSuccessful) {
                    val soldRes = api.getProductSoldStatus(
                        it.serial_number,
                        token
                    )
                    if (soldRes.isSuccessful) {
                        val item = res.body()!!
                        item.soldStatus = soldRes.body()?.soldStatus ?: "0"
                        item.serialNUmber = it.serial_number
                        list.add(
                            item
                        )
                    } else {
                        Log.e(TAG, "getProductNumberList: ${soldRes.raw()}")
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
            val res = api.sellProductApi(
                it,
                token
            )
            if (res.isSuccessful) {
                Log.e(
                    TAG, "buyProduct: Success " + (res.body()?.transactionHash?.receipt?.blockHash
                        ?: " ")
                )
            } else {
                Log.e(TAG, "buyProduct: failed to Buy " + res.raw())
                Log.e(TAG, "buyProduct: failed to Buy ${it.new_owner}")
            }
        }

    }

}
