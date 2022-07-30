package com.flip.warranty.customer.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.customer.reopsitory.GetProductRepositoryImpl
import com.flip.warranty.utility.Globals.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyNowViewModel @Inject constructor(val repository: GetProductRepositoryImpl) : ViewModel() {
    private val productList = MutableLiveData<ArrayList<ProductDetailsData>>()
    val productListUnsold = MutableLiveData<ArrayList<ProductDetailsData>>()
    val productHistoryList = MutableLiveData<ArrayList<ProductDetailsData>>()

    init {
        productList.observeForever {
            productListUnsold.value = it.filter { prod ->
                prod.soldStatus == "0"
            } as ArrayList
        }
        loadDataBuyData()
        loadOrderHistory()
    }

    private fun loadOrderHistory() {
        viewModelScope.launch {
            productHistoryList.value = repository.getOrderHistoryProductList()
        }
    }

    fun loadDataBuyData() {
        viewModelScope.launch {
            productList.value = repository.getProductNumberList()
        }
    }

    fun buyItem(pos: Int) {
        Log.e(TAG, "buyItem: " + productList.value?.get(pos))

        viewModelScope.launch {
            repository.buyProduct(productListUnsold.value!![pos])
        }
        productListUnsold.value!!.removeAt(pos)
        productListUnsold.value = productListUnsold.value
    }

}