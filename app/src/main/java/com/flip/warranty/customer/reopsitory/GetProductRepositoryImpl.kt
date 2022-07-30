package com.flip.warranty.customer.reopsitory

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.flip.warranty.customer.APIs.GetSerialNumberListApi
import com.flip.warranty.customer.dataModel.BuyNowBuyerDetails
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.customer.dataModel.SellinProductToBuyerData
import com.flip.warranty.utility.Globals.TAG
import retrofit2.Response

class GetProductRepositoryImpl(
    val api: GetSerialNumberListApi,
    val sharedPreferences: SharedPreferences
) : GetProductRepository {
    val token = "Bearer " + sharedPreferences.getString("token", " ")!!

    override suspend fun getProductNumberList(): ArrayList<ProductDetailsData> {
        val response = api.getSerialNumberListApi(token)
        val list = ArrayList<ProductDetailsData>()
        Log.e(TAG, "getProductNumberList serial response : ${response.body().toString()}")
        if (response.isSuccessful) {
            response.body()?.product_list?.forEach {
                val serialNumber = it
                val res = api.getProductDetails(
                    serialNumber,
                    token
                )
                if (res.isSuccessful) {
                    val soldRes = api.getProductSoldStatus(
                        serialNumber,
                        token
                    )
                    if (soldRes.isSuccessful) {
                        val item = res.body()!!
                        item.soldStatus = soldRes.body()?.soldStatus ?: "0"
                        item.serialNUmber = serialNumber
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

    override suspend fun buyProduct(data: ProductDetailsData): MutableLiveData<Response<SellinProductToBuyerData>> {
        val output = MutableLiveData<Response<SellinProductToBuyerData>>()

        sharedPreferences.getString("blockChainAddress", " ")?.let {
            BuyNowBuyerDetails(
                serial_number = data.serialNUmber,
                new_owner = it,
                message = "This Product is Secured By FlipSecure"
            )
        }?.let {
            val res = api.sellProductApi(
                it,
                token
            )
            if (res.isSuccessful) {
                output.value = res
                Log.e(
                    TAG, "buyProduct: Success " + (res.body()?.transactionHash?.receipt?.blockHash
                        ?: " ")
                )
            } else {
                Log.e(TAG, "buyProduct: failed to Buy " + res.raw())
                Log.e(TAG, "buyProduct: failed to Buy ${it.new_owner}")
            }
        }
        return output
    }

    override suspend fun getSingleProductDetail(serialNumber: String): ProductDetailsData {
        val res = api.getProductDetails(
            serialNumber,
            token
        )
        if (res.isSuccessful) {
            res.body()!!.serialNUmber = serialNumber
            return res.body()!!
        } else {
            res.headers()
            res.message()
        }
        return res.body()!!
    }

    override suspend fun getOrderHistoryProductList(): ArrayList<ProductDetailsData> {
        val response = api.getOrderHistoryApi(token)
        val list = ArrayList<ProductDetailsData>()
        if (response.isSuccessful) {
            Log.e(TAG, "getOrderHistoryProductList: ${response.body().toString()}")
            response.body()?.owned_products?.forEach {
                val serialNumber = it
                val res = api.getProductDetails(
                    serialNumber,
                    token
                )
                if (res.isSuccessful) {
                    val soldRes = api.getProductSoldStatus(
                        serialNumber,
                        token
                    )
                    if (soldRes.isSuccessful) {
                        val item = res.body()!!
                        item.soldStatus = soldRes.body()?.soldStatus ?: "0"
                        item.serialNUmber = serialNumber
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
        } else {
            Log.e(TAG, "getOrderHistoryProductList: failed to get history")
        }
        return list
    }


}
